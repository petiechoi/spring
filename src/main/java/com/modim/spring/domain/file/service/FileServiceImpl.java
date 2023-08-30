package com.modim.spring.domain.file.service;

import com.modim.spring.domain.file.dto.FileDto;
import com.modim.spring.domain.file.model.File;
import com.modim.spring.domain.file.repository.FileRepository;
import com.modim.spring.domain.member.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    private final CurrentMemberUtil currentMemberUtil;

    @Override
    public List<File> list() {
        return fileRepository.findAll();
    }

    @Override
    @Transactional
    public String delete(Long id) {
        File file = fileRepository.findById(id).orElseThrow(() -> new RuntimeException("책을 찾을 수 없습니다."));
        String s3fileName = file.getFileId();
        fileRepository.deleteById(id);
        return s3fileName;
    }

    @Override
    @Transactional
    public void create(MultipartFile multipartFile, String storeFileName) {
        String creator = currentMemberUtil.GetCurrentMemberId();
        if (creator.length() == 0) {
            // 오류 핸들러
            return;
        }
        FileDto fileDto = FileDto.builder()
                .fileName(multipartFile.getOriginalFilename())
                .creator(creator)
                .build();
        File file = fileDto.toEntity(storeFileName);
        fileRepository.save(file);
    }
}
