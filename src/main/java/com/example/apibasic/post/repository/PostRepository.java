// 2023-01-16
package com.example.apibasic.post.repository;

import com.example.apibasic.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
