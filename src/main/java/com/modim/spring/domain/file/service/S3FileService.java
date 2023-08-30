package com.modim.spring.domain.file.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.modim.spring.global.response.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class S3FileService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;


    private File convert(MultipartFile multipartFile) throws IOException{
        File convFile = new File(multipartFile.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(convFile);
        fileOutputStream.write(multipartFile.getBytes());;
        fileOutputStream.close();
        return convFile;
    }

    public Response create(MultipartFile multipartFile, String storeFileName) throws IOException {
        try{
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, storeFileName, convert(multipartFile));
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);  // URL 접근시 권한 읽을수 있도록 설정.
            amazonS3.putObject(putObjectRequest);
        } catch (AmazonServiceException ase){
            return Response.error(ase.getMessage());
        } catch (AmazonClientException ace){
            return Response.error(ace.getMessage());
        }
        return Response.success();
    }

    public Response delete(String s3fileName){
        try{
            amazonS3.deleteObject(bucket, s3fileName);
        }catch (AmazonServiceException ase){
            return Response.error(ase.getMessage());
        }
        return Response.success();
    }


    public Response getObject(String fileName) {
        try{
            S3Object o =amazonS3.getObject(new GetObjectRequest(bucket, fileName));
        }catch (AmazonServiceException ase){
            return Response.error(ase.getMessage());
        }
        return Response.success();
    }

    public List<String> fileList(){
        List<String> fileList = new ArrayList<>();
        ObjectListing objects = amazonS3.listObjects(bucket);
        do {
            //1000개 단위로 읽음
            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                fileList.add(objectSummary.getKey());
            }
            objects = amazonS3.listNextBatchOfObjects(objects);
        } while (objects.isTruncated());
        return fileList;
    }

}
