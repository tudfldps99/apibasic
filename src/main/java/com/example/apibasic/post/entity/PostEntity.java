// 2023-01-16
package com.example.apibasic.post.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="postNo")
@Builder

@Entity     // 테이블 생성
@Table(name = "tbl_post")       // 테이블 명 변경
public class PostEntity {       // Entity 에서 validation 하지 말기. validation 은 DTO 에서.
    @Id         // NOT NULL, Unique
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNo;        // 게시물 식별번호     --> DB 에는 post_no로 저장됨

    @Column(nullable = false)
    private String writer;      // 작성자

    @Column(nullable = false)
    private String title;       // 제목

    private String content;     // 내용

//    private List<String> hashTags;      // 해시태그 목록

    @CreationTimestamp
    private LocalDateTime createDate;   // 작성 시간        --> INSERT

    @UpdateTimestamp
    private LocalDateTime modifyDate;   // 수정 시간        --> INSERT, UPDATE
}
