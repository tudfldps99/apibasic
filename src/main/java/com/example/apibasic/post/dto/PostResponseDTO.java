// 2023-01-13
package com.example.apibasic.post.dto;

import com.example.apibasic.post.entity.PostEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

// 클라이언트가 요구하는 정보들만
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class PostResponseDTO {
    private String author;      // writer 대신 author 로 달라고 요구
    private String title;
    private String content;
    private List<String> hashTags;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime regDate;      // createDate 대신 regDate 로 + 연/월/일 만 표시 되게끔

    // 엔터티를 DTO 로 변환하는 생성자
    public PostResponseDTO(PostEntity entity) {
        this.author = entity.getWriter();
        this.content = entity.getContent();
        this.title = entity.getTitle();
        this.regDate = entity.getCreateDate();
        this.hashTags = entity.getHashTags();
    }

}