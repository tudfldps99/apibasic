// 2023-01-18
package com.example.apibasic.post.repository;

import com.example.apibasic.post.entity.HashTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository<HashTagEntity, Long> {
}
