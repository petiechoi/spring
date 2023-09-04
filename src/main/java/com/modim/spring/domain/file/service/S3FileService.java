package com.modim.spring.domain.file.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.modim.spring.global.response.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class S3FileService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

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
    private File convert(MultipartFile multipartFile) throws IOException{
        File convFile = new File(multipartFile.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(convFile);
        fileOutputStream.write(multipartFile.getBytes());;
        fileOutputStream.close();
        return convFile;
    }

    public Response delete(String s3fileName){
        try{
            amazonS3.deleteObject(bucket, s3fileName);
        }catch (AmazonServiceException ase){
            return Response.error(ase.getMessage());
        }
        return Response.success();
    }

    public ResponseEntity<byte[]> download(String fileId, String fileName) throws IOException {
        try{
            S3Object o =amazonS3.getObject(new GetObjectRequest(bucket, fileId));
            S3ObjectInputStream objectInputStream = ((S3Object) o).getObjectContent();

            byte[] bytes = IOUtils.toByteArray(objectInputStream);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            httpHeaders.setContentLength(bytes.length);
            httpHeaders.setContentDispositionFormData("attachment", fileName);
            return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);

        } catch (AmazonServiceException ase){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    // 미사용
//    public List<String> list(){
//        List<String> fileList = new ArrayList<>();
//        ObjectListing objects = amazonS3.listObjects(bucket);
//        do {
//            //1000개 단위로 읽음
//            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
//                fileList.add(objectSummary.getKey());
//            }
//            objects = amazonS3.listNextBatchOfObjects(objects);
//        } while (objects.isTruncated());
//        return fileList;
//    }
}
