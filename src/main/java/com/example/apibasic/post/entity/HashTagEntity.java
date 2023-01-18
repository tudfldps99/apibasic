// 2023-01-18
package com.example.apibasic.post.entity;

import lombok.*;
import javax.persistence.*;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@Entity
@Table(name = "tbl_hash_tag")
public class HashTagEntity {        // 한 개의 게시물(PostEntity, 1) 당 해시태그(HashTagEntity, M) 여러개

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "post_no")
    private PostEntity post;
}
