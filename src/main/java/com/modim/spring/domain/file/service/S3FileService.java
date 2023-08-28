package com.modim.spring.domain.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class S3FileService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

//    public String createFileName(String fileName){
//        return UUID.randomUUID().toString().concat(fileName);
//    }
//
//    public static byte[] downloadFile(String fileName){
//        S3Object s3Object = amazonS3Client.getObject(bucket, fileName);
//        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
//        try{
//            byte[] content = IOUtils.toByteArray(s3ObjectInputStream);
//            return content;
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public String downloadFile(MultipartFile multipartFile, String filePath)
//    {
//        String fileNme = filePath + "/" + UUID.randomUUID();
//
//        String
//    }

    public ResponseEntity<byte[]> getObject(String filename) throws IOException {
        S3Object o =amazonS3.getObject(new GetObjectRequest(bucket, filename));
        S3ObjectInputStream objectInputStream = ((S3Object) o).getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        String getFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", filename);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
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
