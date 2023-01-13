// 2023-01-13
package com.example.apibasic.post.api;

import com.example.apibasic.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequiredArgsConstructor    // final 초기화 생성자
@RequestMapping("/posts")       // 첫번째 경로
public class PostApiController {        /* 서빙 직원 */

    // PostRepository 에게 의존하는 관계 -> 100개 클래스가 의존하고 있다면, 주방장 변경 시 100개 클래스 모두 수정해야 함
    // --> 주방장이 바뀌든 말든 서빙 직원은 신경 X : 제어의 역전
    // 1. 필드 주입, 2. 생성자 주입
    private final PostRepository postRepository;      // 제어의 역전         // final 넣으면 완전한 불변성 상태
    //  private final PostRepository postRepository = new PostRepository()    -> 를 Spring 에서 생성자를 통해 넣어줌

    //@Autowired     // 스프링 컨테이너에게 의존객체를 자동주입해 달라
    // 의존 객체를 누군가가(스프링 컨테이너) 주입해줌 = 의존성 주입
//    public PostApiController(PostRepository postRepository) {       // 생성자를 통한 주입 : 생성자 주입
//        this.postRepository = postRepository;
//    }

//    public void setPostRepository(PostRepository postRepository) {  // setter 를 통한 주입 : setter 주입 --> 사용 금지
//        this.postRepository = postRepository
//    }
    // setter 는 내가 원하면 아무때나 반복호출 가능 but 불변성을 유지해야 함
    // 주방장이 계속 바뀔 가능성이 있음


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
