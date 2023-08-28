package com.modim.spring.domain.file.service;

import com.modim.spring.domain.file.model.File;
import com.modim.spring.domain.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    @Override
    public List<File> list() {
        return fileRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        fileRepository.deleteById(id);
    }

    @Override
    public void create(File file) {
        fileRepository.save(file);
    }
}
