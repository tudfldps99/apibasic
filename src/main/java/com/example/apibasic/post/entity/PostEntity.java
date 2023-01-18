// 2023-01-16
package com.example.apibasic.post.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // 2023-01-18) 연관관계. 게시물과 해시태그 (1대 다(M) 관계) ==> HashTag 테이블 생성 필요 (HashTagEntity.java)
    // 양방향매핑으로
    @OneToMany(mappedBy = "post")
    private List<HashTagEntity> hashTags = new ArrayList<>();   // 해시태그 목록

    @CreationTimestamp
    private LocalDateTime createDate;   // 작성 시간        --> INSERT

    @UpdateTimestamp
    private LocalDateTime modifyDate;   // 수정 시간        --> INSERT, UPDATE
}
