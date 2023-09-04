package com.modim.spring.domain.file.controller;

import com.modim.spring.domain.file.service.FileServiceImpl;
import com.modim.spring.domain.file.service.S3FileService;
import com.modim.spring.global.response.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileController {

    final private FileServiceImpl fileService;
    final private S3FileService s3FileService;

    @PostMapping("/files")
    public ResponseEntity<Response> create(@RequestParam("file")MultipartFile multipartFile) throws IOException {
        if( !multipartFile.isEmpty()) {
            String storeFileName = createStoreFileName(multipartFile.getOriginalFilename());
            if( fileService.create(multipartFile, storeFileName) )
                return new ResponseEntity<>(s3FileService.create(multipartFile, storeFileName), HttpStatus.OK);
            else return new ResponseEntity<>(Response.error("재 로그인이 필요합니다."),HttpStatus.OK);
        }
        return new ResponseEntity<>(Response.error("파일이 없습니다."),HttpStatus.OK);
    }

    private String createStoreFileName(String originFileName){
        return UUID.randomUUID().toString() + "." + extractExt(originFileName);
    }

    private String extractExt(String originFileName) {
        int pos = originFileName.lastIndexOf(".");
        return originFileName.substring(pos + 1);
    }

    @DeleteMapping("/files/{id}")
    public ResponseEntity<Response> delete(@PathVariable(value="id")Long id){
        String s3fileName = fileService.delete(id);
        return new ResponseEntity<>(s3FileService.delete(s3fileName), HttpStatus.OK);
    }

    @GetMapping("/files/{fileId}/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable String fileId, @PathVariable String fileName) throws IOException {
        return s3FileService.download(fileId, fileName);
    }
}
