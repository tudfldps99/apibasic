// 2023-01-13
package com.example.apibasic.post.dto;

import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PostUpdateDTO {        // 게시물 수정은 제목이랑 내용만 수정 가능
    private String title;
    private String content;
}
