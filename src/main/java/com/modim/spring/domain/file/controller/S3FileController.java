package com.modim.spring.domain.file.controller;

import com.modim.spring.domain.file.service.S3FileService;
import com.modim.spring.global.response.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Response> download(@PathVariable String fileName) throws IOException {
        return new ResponseEntity<>(s3FileService.getObject(fileName), HttpStatus.OK);
    }

}
