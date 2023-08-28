package com.modim.spring.domain.file.controller;

import com.modim.spring.domain.file.service.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class S3FileController {
    private final S3FileService s3FileService;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


//    @GetMapping("/download/{fileName}")
////    public ResponseEntity<Response> downloadFile(@PathVariable String fileName){
//    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName){
//        byte[] data = S3FileService.downloadFile(fileName);
//        ByteArrayResource resource = new ByteArrayResource(data);
//        //return new ResponseEntity<>(Response.success(), HttpStatus.OK);
//        return ResponseEntity
//                .ok()
//                .contentLength(data.length)
//                .header("Content-type","application/octet-stream")
//                .header("Content-disposition","attachment; filename=\"" + fileName + "\"")
//                .body(resource);
//    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable String fileName) throws IOException {
        return s3FileService.getObject(fileName);
    }

}
