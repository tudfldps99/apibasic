// 2023-01-13
package com.example.apibasic.post.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// resource : 게시물 (Post)
/*
    게시물 목록 조회:  /posts     - GET
    게시물 개별 조회: /posts/{id} - GET
    게시물 등록:     /posts      - POST
    게시물 수정:     /posts/{id} - PATCH (PUT)
    게시물 삭제:     /posts/{id} - DELETE
 */
@RestController
@Slf4j
@RequestMapping("/posts")       // 첫번째 경로
public class PostApiController {

    // 게시물 목록 조회
    @GetMapping
    public ResponseEntity<?> list() {
        log.info("/posts GET request");
        return null;
    }

    // 게시물 개별 조회
    @GetMapping("/{postNo}")
    public ResponseEntity<?> detail(@PathVariable Long postNo) {
        // @PathVariable("postNo") Long postNo = @PathVariable 내부 값과 변수 명이 동일하면 @PathVariable 내부 값 생략 가능

        log.info("/posts/{} GET request", postNo);
        return null;
    }

    // 게시물 등록
    @PostMapping
    public ResponseEntity<?> create() {
        log.info("/posts POST request");
        return null;
    }

    // 게시물 수정
    @PatchMapping("/{postNo}")
    public ResponseEntity<?> modify(@PathVariable Long postNo) {
        log.info("/posts/{} PATCH request", postNo);
        return null;
    }

    // 게시물 삭제
    @DeleteMapping("/{postNo}")
    public ResponseEntity<?> remove(@PathVariable Long postNo) {
        log.info("/posts/{} DELETE request", postNo);
        return null;
    }
}
