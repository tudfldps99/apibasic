// 2023-01-13
package com.example.apibasic.post.dto;

import com.example.apibasic.post.entity.PostEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

// 게시물 쓸 때 필요한 정보들만
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class PostCreateDTO {    // DTO 가 검증처리도 해줌

    private String writer;
    private String title;
    private String content;
    private List<String> hashTags;

    // PostEntity 로 변환하는 유틸 메서드
    public PostEntity toEntity() {
        return PostEntity.builder()
                .postNo(PostEntity.sequence++)       // postNo 숫자 증가는 PostEntity.java 에서 처리 (sequence)
                .writer(this.writer)                 // this.OOO : client 에서 받아온 값 그대로
                .content(this.content)
                .title(this.title)
                .hashTags(this.hashTags)
                .createDate(LocalDateTime.now())
                .build();
    }
}
