// 2023-01-17
package com.example.apibasic.post.repository;

import com.example.apibasic.post.dto.PageRequestDTO;
import com.example.apibasic.post.entity.PostEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
class PageTest {

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void bulkInsert() {
        for (int i = 1; i <= 500; i++) {        // fori : for문
            PostEntity post = PostEntity.builder()
                    .title("제목" + i)
                    .writer("작성자" + i)
                    .content("내용" + i)
                    .build();
            postRepository.save(post);
        }
    }

    @Test
    void pagingTest() {

        // 페이지 정보 생성
        // PageRequest pageInfo = PageRequest.of(1, 10);           // = SELECT * FROM tbl_post ORDER BY create_date DESC LIMIT 10, 20;
                                                                   // PageRequest.of(0, 10) : 1페이지 10개(1~10), PageRequest.of(1, 10) : 2페이지 10개 (11~20)
        // 클라이언트가 전달한 페이지 정보
        PageRequestDTO dto = PageRequestDTO.builder()
                .page(4)
                .sizePerPage(10)
                .build();

        PageRequest pageInfo = PageRequest.of(
                dto.getPage() - 1,
                dto.getSizePerPage(),
                Sort.Direction.DESC,            // 내림차 정렬
                "createDate"        // 정렬 기준 필드
        );

        Page<PostEntity> postEntities = postRepository.findAll(pageInfo);

        // 실제 조회된 데이터 (getContent) + 추가 정보들 존재 (총 데이터 수, 이전&이후 페이지의 유무, ...) --> Page 로 감싼 후 List 로 한번 더 감싸는 이유
        List<PostEntity> content = postEntities.getContent();

        int totalPages = postEntities.getTotalPages();
        long totalElements = postEntities.getTotalElements();

        System.out.println("content.size() = " + content.size());
        System.out.println("totalPages = " + totalPages);
        System.out.println("totalElements = " + totalElements);

        content.forEach(System.out::println);
    }


    @Test
    @DisplayName("제목에 3이 포함된 결과를 검색 후 1페이지 정보를 조회해야 한다")
    void pageTest2() {
        // given
        String title = "3";
        PageRequest pageRequest = PageRequest.of(
                0,
                10,
                Sort.Direction.DESC,
                "createDate");

        // Page upcast : Slice
        Slice<PostEntity> postEntityPage = postRepository.findByTitleContaining(title, pageRequest);

        List<PostEntity> content = postEntityPage.getContent();

        boolean next = postEntityPage.hasNext();
        boolean prev = postEntityPage.hasPrevious();        // false : 1페이지 이전은 없기 때문

        System.out.println("next = " + next);
        System.out.println("prev = " + prev);

        content.forEach(System.out::println);
    }
}
