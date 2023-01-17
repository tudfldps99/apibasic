// 2023-01-16
package com.example.apibasic.post.repository;

import com.example.apibasic.post.entity.PostEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest  // 스프링 컨테이너를 사용해서 스프링이 관리하는 객체를 주입받는 기능
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void bulkInsert() {
        PostEntity savePost1 = PostEntity.builder()
                .writer("작성자1")
                .title("제목1")
                .content("내용1")
                .build();
        PostEntity savePost2 = PostEntity.builder()
                .writer("작성자2")
                .title("제목2")
                .content("내용2")
                .build();
        PostEntity savePost3 = PostEntity.builder()
                .writer("작성자3")
                .title("제목3")
                .content("내용3")
                .build();

        postRepository.save(savePost1);
        postRepository.save(savePost2);
        postRepository.save(savePost3);
    }

    // 게시물 전체 조회
    @Test
    @DisplayName("게시물 목록을 조회하면 3개의 게시물 정보가 조회되어야 한다")
    @Transactional
    @Rollback
    void findAllTest() {
        // given
        //when
        List<PostEntity> postEntityList = postRepository.findAll();

        //then
        assertEquals(3, postEntityList.size());
        assertEquals("작성자2", postEntityList.get(1).getWriter());

        System.out.println("\n==================================");
        postEntityList.forEach(System.out::println);
        System.out.println("==================================");
    }

    // 게시물 개별 조회
    @Test
    @DisplayName("3번 게시물의 내용은 '내용3' 이다")
    @Transactional
    @Rollback
    void findOneTest() {
        Long postId = 3L;

        Optional<PostEntity> foundPost = postRepository.findById(postId);
        assertEquals("내용3", foundPost.get().getContent());
    }

    // 게시물 삭제
    @Test
    @DisplayName("하나의 게시물을 삭제해야 한다")
    @Transactional
    @Rollback
    void deleteTest() {
        // given
        Long postId = 2L;

        // when
        postRepository.deleteById(postId);
        Optional<PostEntity> foundPost = postRepository.findById(postId);

        // then
        assertFalse(foundPost.isPresent());
        assertEquals(2, postRepository.findAll().size());
    }

    // 게시물 수정
    @Test
    @DisplayName("2번 게시물의 제목과 내용을 수정해야 한다")
    @Transactional
    @Rollback
    void modifyTest() {
        // given
        Long postId = 2L;
        String newTitle = "새로운 제목";
        String newContent = "새로운 내용";

        // when
        Optional<PostEntity> foundPost = postRepository.findById(postId);
        foundPost.ifPresent(p -> {
            p.setTitle(newTitle);
            p.setContent(newContent);
            postRepository.save(p);
        });
        // 수정 후 조회
        Optional<PostEntity> modifiedPost = postRepository.findById(postId);

        System.out.println("\n==================================");
        System.out.println(modifiedPost);
        System.out.println("==================================");

        // then
        assertEquals("새로운 제목", modifiedPost.get().getTitle());
        assertEquals("새로운 내용", modifiedPost.get().getContent());
    }
}
