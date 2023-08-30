package com.modim.spring.domain.file.dto;

import com.modim.spring.domain.file.model.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDto {
    private String fileName;
    private String creator;

    public File toEntity(String fileId){
        return File.builder()
                .fileId(fileId)
                .fileName(fileName)
                .creator(creator)
                .build();
    }
}
