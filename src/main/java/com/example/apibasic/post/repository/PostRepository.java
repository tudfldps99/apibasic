// 2023-01-16
package com.example.apibasic.post.repository;

import com.example.apibasic.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<PostEntity, Long> {

    // 2023-01-17
    // 제목으로 검색 후 페이징처리
    Page<PostEntity> findByTitleContaining(String title, Pageable pageable);        // springframework 의 Pageable
}
