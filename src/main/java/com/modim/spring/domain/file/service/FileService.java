package com.modim.spring.domain.file.service;

import com.modim.spring.domain.file.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService{
    // 파일 전체목록
    List<File> list();
    // 파일 삭제
    String delete(Long id);

    // 파일 생성
    void create(MultipartFile multipartFile, String storeFileName);
}
