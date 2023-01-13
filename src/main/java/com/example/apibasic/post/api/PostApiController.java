// 2023-01-13
package com.example.apibasic.post.api;

import com.example.apibasic.post.dto.PostCreateDTO;
import com.example.apibasic.post.dto.PostResponseDTO;
import com.example.apibasic.post.dto.PostResponseOneDTO;
import com.example.apibasic.post.dto.PostUpdateDTO;
import com.example.apibasic.post.entity.PostEntity;
import com.example.apibasic.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
@RequiredArgsConstructor        // final 을 초기화 해주는 생성자를 자동으로 만들어줌. 의존 객체가 자동주입되는 효과 (줄 38~42)
@RequestMapping("/posts")       // 첫번째 경로
public class PostApiController {        /* 서빙 직원 */

    // PostRepository 에게 의존하는 관계 -> 100개 클래스가 의존하고 있다면, 주방장 변경 시 100개 클래스 모두 수정해야 함
    // --> 주방장이 바뀌든 말든 서빙 직원은 신경 X : 제어의 역전
    // 1. 필드 주입, 2. 생성자 주입
    private final PostRepository postRepository;      // 제어의 역전         // final 넣으면 완전한 불변성 상태
    //  private final PostRepository postRepository = new PostRepository()    -> new PostRepository() 를 Spring 에서 생성자를 통해 넣어줌

/*
    @Autowired     // 스프링 컨테이너에게 의존객체를 자동주입해 달라
    // 의존 객체를 누군가가(스프링 컨테이너) 주입해줌 = 의존성 주입
    public PostApiController(PostRepository postRepository) {       // 생성자를 통한 주입 : 생성자 주입
        this.postRepository = postRepository;
    }
*/

//    public void setPostRepository(PostRepository postRepository) {  // setter 를 통한 주입 : setter 주입 --> but, 사용 금지
//        this.postRepository = postRepository
//    }
    // setter 는 내가 원하면 아무때나 반복호출 가능 but 불변성을 유지해야 함
    /* 주방장이 계속 바뀔 가능성이 있음 */

    // 게시물 목록 조회
    /*
    @GetMapping
    public ResponseEntity<?> list() {
        log.info("/posts GET request");

        List<PostEntity> list = postRepository.findAll();

        return ResponseEntity
                .ok()
                .body(list);
    }
     */ // '글번호 필요없고, modifyDate 필요없고, createDate 를 regDate로 바꿔서 연/월/일 만 주세요' 와 같이 클라이언트의 요구사항대로 조회되려면?
        // 즉, 클라이언트가 달라는 정보만 주기 --> dto 이용 (PostResponseDTO.java)
    @GetMapping
    public ResponseEntity<?> list() {
        log.info("/posts GET request");

        List<PostEntity> list = postRepository.findAll();

        // 엔터티 리스트를 DTO 리스트로 변환해서 클라이언트에 응답 --> PostResponseDTO.java 에서 처리
        List<PostResponseDTO> responseDTOList = list.stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(responseDTOList);
    }

    // 게시물 개별 조회 - 클라이언트에게 추가로 수정시간 정보를 제공
    /*
    @GetMapping("/{postNo}")
    public ResponseEntity<?> detail(@PathVariable Long postNo) {
        // @PathVariable("postNo") Long postNo = @PathVariable 내부 값과 변수 명이 동일하면 @PathVariable 내부 값 생략 가능

        log.info("/posts/{} GET request", postNo);

        PostEntity post = postRepository.findOne(postNo);

        return ResponseEntity
                .ok()
                .body(post);        // ctrl + alt + n : body 안에 넣어줌 , ctrl + alt + v : body 밖으로 빼줌
    }
     */
    @GetMapping("/{postNo}")
    public ResponseEntity<?> detail(@PathVariable Long postNo) {
        log.info("/posts/{} GET request", postNo);

        PostEntity post = postRepository.findOne(postNo);

        // dto 이용 (PostResponseOneDTO)
        PostResponseOneDTO postResponseOneDTO = new PostResponseOneDTO(post);
        return ResponseEntity
                .ok()
                .body(postResponseOneDTO);
    }

    // 게시물 등록
    /*
    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostEntity entity) {
        log.info("/posts POST request");
        log.info("게시물 정보: {}", entity);

        boolean flag = postRepository.save(entity);
        return flag
                ? ResponseEntity.ok().body("INSERT-SUCCESS")
                : ResponseEntity.badRequest().body("INSERT_FAIL");
    }
    */  // --> entity 에는 너무 많은 정보가 있음. 받을 정보만 받기 --> dto 이용 (PostCreateDTO.java)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostCreateDTO createDTO) {     // -> 1) DTO 로 받아왔지만, (data 4개)
        log.info("/posts POST request");
        log.info("게시물 정보: {}", createDTO);

        // dto 를 entity 로 변환하는 작업 필요 (글번호, 작성 시간 자동 생성)
        PostEntity entity = createDTO.toEntity();

        boolean flag = postRepository.save(entity);     // -> 2) entity 로 보내야 함 (data 6개)
        return flag
                ? ResponseEntity.ok().body("INSERT-SUCCESS")
                : ResponseEntity.badRequest().body("INSERT_FAIL");
    }

    // 게시물 수정 - 제목, 내용만 수정 가능, 수정시간은 현재시간으로 자동 수정
    /*
    @PatchMapping("/{postNo}")
    public ResponseEntity<?> modify(@PathVariable Long postNo) {
        log.info("/posts/{} PATCH request", postNo);
        return null;
    }
     */
    @PatchMapping("/{postNo}")
    public ResponseEntity<?> modify(@PathVariable Long postNo, @RequestBody PostUpdateDTO updateDTO) {
        log.info("/posts/{} PATCH request", postNo);
        log.info("수정할 정보: {}", updateDTO);

        // 수정 전 데이터 조회하기
        PostEntity entity = postRepository.findOne(postNo);

        // 수정 진행
        String modTitle = updateDTO.getTitle();
        String modContent = updateDTO.getContent();

        if (modTitle != null) entity.setTitle(modTitle);
        if (modContent != null) entity.setContent(modContent);
        entity.setModifyDate(LocalDateTime.now());

        boolean flag = postRepository.save(entity);
        return flag
                ? ResponseEntity.ok().body("UPDATE-SUCCESS")
                : ResponseEntity.badRequest().body("UPDATE_FAIL");
    }

    // 게시물 삭제
    @DeleteMapping("/{postNo}")
    public ResponseEntity<?> remove(@PathVariable Long postNo) {
        log.info("/posts/{} DELETE request", postNo);

        boolean flag = postRepository.delete(postNo);
        return flag
                ? ResponseEntity.ok().body("DELETE-SUCCESS")
                : ResponseEntity.badRequest().body("DELETE_FAIL");
    }
}
