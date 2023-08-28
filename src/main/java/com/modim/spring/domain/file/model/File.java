package com.modim.spring.domain.file.model;

import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("s3파일명")
    @Column(unique = true)
    private String fildId;

    @Comment("원본파일명")
    private String fileName;

    @Comment("만든사람")
    private String creator;

    @Comment("생성일")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
}
