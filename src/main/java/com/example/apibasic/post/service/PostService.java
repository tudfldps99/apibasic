// 2023-01-16
package com.example.apibasic.post.service;

import com.example.apibasic.post.dto.*;
import com.example.apibasic.post.entity.PostEntity;
import com.example.apibasic.post.repository.PostRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
// 스프링빈 등록 필요 (스프링에게 라이프사이클 제어권을 넘김)
@Service
public class PostService {

    // service 가 repository 에 의존
    private final PostRepository postRepository;        // IoC (제어의 역전)

    // 목록 조회 중간처리
    public PostListResponseDTO getList() {          // throws(던지다) RuntimeException  : RuntimeException 이 발생하면 호출한 클래스로 던짐
        List<PostEntity> list = postRepository.findAll();

        if (list.isEmpty()) {       // 예외처리
            throw new RuntimeException("조회 결과가 없습니다");      // throw(유발하다, 일어나다) new RuntimeException : 밑의 코드를 진행시키지 않기 위해 강제로 에러를 발생시킴
        }
        // 엔터티 리스트를 DTO 리스트로 변환해서 클라이언트에 응답
        List<PostResponseDTO> responseDTOList = list.stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());

        PostListResponseDTO listResponseDTO = PostListResponseDTO.builder()
                .count(responseDTOList.size())
                .posts(responseDTOList)
                .build();

        return listResponseDTO;
    }

    // 개별 조회 중간처리
    public PostResponseOneDTO getDetail(Long postNo) {
        PostEntity post = postRepository.findOne(postNo);

        if (post == null) {
            throw new RuntimeException(post + "번 게시물이 존재하지 않습니다");
        }
        // 엔터티를 DTO 로 변환
        return new PostResponseOneDTO(post);
    }

    // 등록 중간처리
    public boolean insert(final PostCreateDTO createDTO) {     // final : DB로 넘어가기 전에 DTO 가 변경되는 것을 방지할 수 있음
        // dto 를 entity 변환 작업
        final PostEntity entity = createDTO.toEntity();

        return postRepository.save(entity);
    }

    // 수정 중간처리
    public boolean update(final Long postNo, PostUpdateDTO updateDTO) {
        // 수정 전 데이터 조회하기
        final PostEntity entity = postRepository.findOne(postNo);

        // 수정 진행
        String modTitle = updateDTO.getTitle();
        String modContent = updateDTO.getContent();

        if (modTitle != null) entity.setTitle(modTitle);
        if (modContent != null) entity.setContent(modContent);
        entity.setModifyDate(LocalDateTime.now());

        return postRepository.save(entity);
    }

    public boolean delete(final Long postNo) {
        return postRepository.delete(postNo);
    }
}
