package com.modim.spring.domain.file.model;

import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private String fileId;

    @Comment("원본파일명")
    private String fileName;

    @Comment("만든사람")
    private String creator;

    @Comment("생성일")
    private String createdAt;

    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
