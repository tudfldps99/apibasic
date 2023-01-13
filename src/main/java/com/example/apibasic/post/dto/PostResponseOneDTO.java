// 2023-01-13
package com.example.apibasic.post.dto;

import com.example.apibasic.post.entity.PostEntity;
import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PostResponseOneDTO extends PostResponseDTO {       // 상속

    private LocalDateTime modifyDate;

    // 엔터티를 DTO 로 변환하는 생성자
    public PostResponseOneDTO(PostEntity entity) {
        super(entity);      // 부모 클래스(PostResponseDTO) 생성자 5개
        this.modifyDate = entity.getModifyDate();   // + 수정 데이터 1개
    }
}
