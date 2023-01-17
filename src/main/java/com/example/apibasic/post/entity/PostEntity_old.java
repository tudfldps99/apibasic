// 2023-01-13
package com.example.apibasic.post.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

// 게시물의 데이터 자바빈즈
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class PostEntity_old {
    // dto : 요청, 응답 할 때 데이터 담는 그릇 (필요한 데이터만)
    // entity : DB Access 할 때 데이터 담는 그릇 (모든 데이터)

    public static long sequence = 1L;   // 연속된 일련번호 (초기값 : 1)

    private Long postNo;        // 게시물 식별번호
    private String writer;      // 작성자
    private String title;       // 제목
    private String content;     // 내용
    private List<String> hashTags;      // 해시태그 목록
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")        // 2023-01-17) --> DTO 에서 해주기
    private LocalDateTime createDate;   // 작성 시간
    private LocalDateTime modifyDate;   // 수정 시간
}
