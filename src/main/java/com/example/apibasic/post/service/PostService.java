// 2023-01-16
package com.example.apibasic.post.service;

import com.example.apibasic.post.dto.*;
import com.example.apibasic.post.entity.HashTagEntity;
import com.example.apibasic.post.entity.PostEntity;
import com.example.apibasic.post.repository.HashTagRepository;
import com.example.apibasic.post.repository.PostRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
// 스프링빈 등록 필요 (스프링에게 라이프사이클 제어권을 넘김)
@Service
public class PostService {

    // service 가 repository 에 의존
    private final PostRepository postRepository;        // IoC (제어의 역전)

    private final HashTagRepository hashTagRepository;      // 2023-01-18) HashTagRepository 추가

//--------------------------------------
    // 목록 조회 중간처리
    public PostListResponseDTO getList(PageRequestDTO pageRequestDTO) {          // throws(던지다) RuntimeException  : RuntimeException 이 발생하면 호출한 클래스로 던짐

        // getList 에 파라미터 pageRequestDTO 추가하기 위해 수정
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSizePerPage(),
                Sort.Direction.DESC,
                "createDate"
        );

//        List<PostEntity> list = postRepository.findAll();
        final Page<PostEntity> pageData = postRepository.findAll(pageable);
        List<PostEntity> list = pageData.getContent();

        if (list.isEmpty()) {       // 예외처리
            throw new RuntimeException("조회 결과가 없습니다");      // throw(유발하다, 일어나다) new RuntimeException : 밑의 코드를 진행시키지 않기 위해 강제로 에러를 발생시킴
        }

        // 엔터티 리스트를 DTO 리스트로 변환해서 클라이언트에 응답
        List<PostResponseDTO> responseDTOList = list.stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());

        PostListResponseDTO listResponseDTO = PostListResponseDTO.builder()
                .count(responseDTOList.size())
                .pageInfo(new PageResponseDTO(pageData))
                .posts(responseDTOList)
                .build();

        return listResponseDTO;
    }

//--------------------------------------
    // 개별 조회 중간처리
    public PostResponseOneDTO getDetail(Long postNo) {
    /*
        PostEntity post = postRepository.findOne(postNo);

        if (post == null) {
            throw new RuntimeException(post + "번 게시물이 존재하지 않습니다");
        }
    */

        // 2023-01-17) JPA 연결하면서 생긴 오류 해결
        PostEntity post = postRepository
                .findById(postNo)
                .orElseThrow(() -> new RuntimeException(postNo + "번 게시물이 존재하지 않습니다"));
                // orElse : post 가 null 이면 대신에(ELSE) 괄호 안의 행동( 빈 값 (new PostEntity() ), null 이 아니면 PostEntity 값 그대로 들고옴
                // orElseThrow : post 가 null 이면 에러 던짐
                // ==> NULL 체크

        // 엔터티를 DTO 로 변환
        return new PostResponseOneDTO(post);
    }

//--------------------------------------
    // 등록 중간처리
/*
    public boolean insert(final PostCreateDTO createDTO) {     // final : DB로 넘어가기 전에 DTO 가 변경되는 것을 방지할 수 있음

        // dto 를 entity 변환 작업
        final PostEntity entity = createDTO.toEntity();

         return postRepository.save(entity);
    }
*/
    // 2023-01-17) JPA 연결하면서 생긴 오류 해결
    @Transactional      // 2023-01-18) DML 쿼리가 여러개 동시에 나가는 상황에서 트랜잭션 처리
    public PostResponseOneDTO insert(final PostCreateDTO createDTO)     // final : DB로 넘어가기 전에 DTO 가 변경되는 것을 방지할 수 있음
        throws RuntimeException     // 예외처리. 컨트롤러에게 던져버림 (PostApiController.java / 193번째 줄)
    {
        // dto 를 entity 변환 작업
        final PostEntity entity = createDTO.toEntity();

        // entity 가 null 이면 IllegalArgumentException (save 메서드에 ctrl + 클릭하면 알 수 있음)
        // --> save 메서드의 예외처리가 필요함  : 79번째 줄
        PostEntity savedPost = postRepository.save(entity);     // 게시물 save

        // 2023-01-18
        // 1대 다 에서는 '1' 을 먼저 생성 된 후 '다' 가 생성됨
        // hashTag 제외하고 save 되므로, hashTag 를 DB 에 save 해야 함
        List<String> hashTags = createDTO.getHashTags();    // 해시태그 save
                                                            // but, hashTags 가 HashTagEntity 가 아닌, String 형임. --> HashTagEntity 로 변환해줘야 함 : HashTagRepository.java 필요
        // 방법) 해시태그 문자열 리스트에서 문자열들을 하나하나 추출(반복문)한 뒤, 해시태그 엔터티로 만들고 그 엔터티를 데이터베이스에 저장한다.
        List<HashTagEntity> hashTagEntities = new ArrayList<>();
        for (String ht : hashTags) {                                // 1. 하나하나 추출 한 후
            HashTagEntity tagEntity = HashTagEntity.builder()       // 2. 해시태그 엔터티로 만듦
                    .post(savedPost)        // 원본 게시물 정보 넣어줘야 함
                    .tagName(ht)
                    .build();

            HashTagEntity savedTag = hashTagRepository.save(tagEntity);     // 3. 그 엔터티를 DB 에 저장
            hashTagEntities.add(savedTag);          // 트랜잭션이 아직 끝나지 않은 상태이므로, 하단의 return 문인 PostResponseDTO 에서 getHashTags() 를 하지 못하기 때문에  setter 를 이용하여 별도로 저장 필요
        }
        savedPost.setHashTags(hashTagEntities);

        // 저장된 객체를 DTO 로 변환해서 반환
        return new PostResponseOneDTO(savedPost);
    }

//--------------------------------------
    // 수정 중간처리
/*
    public boolean update(final Long postNo, PostUpdateDTO updateDTO) {
        // 수정 전 데이터 조회하기
        final PostEntity entity = postRepository.findOne(postNo);

        // 수정 진행 (제목과 내용만 수정 가능)
        String modTitle = updateDTO.getTitle();
        String modContent = updateDTO.getContent();

        if (modTitle != null) entity.setTitle(modTitle);
        if (modContent != null) entity.setContent(modContent);
        entity.setModifyDate(LocalDateTime.now());

        return postRepository.save(entity);
    }
*/
    // 2023-01-17) JPA 연결하면서 생긴 오류 해결
    public PostResponseOneDTO update(final Long postNo, PostUpdateDTO updateDTO)
        throws RuntimeException {

        // 수정 전 데이터 조회하기
        final PostEntity entity = postRepository
                .findById(postNo)
                .orElseThrow(() -> new RuntimeException("수정 전 데이터가 존재하지 않습니다."));

        // 수정 진행 (제목과 내용만 수정 가능)
        String modTitle = updateDTO.getTitle();
        String modContent = updateDTO.getContent();

        if (modTitle != null) entity.setTitle(modTitle);
        if (modContent != null) entity.setContent(modContent);

        PostEntity modifiedPost = postRepository.save(entity);

        // 저장된 객체를 DTO 로 변환해서 반환
        return new PostResponseOneDTO(modifiedPost);
    }

//----------------------------------------------
/*
    public boolean delete(final Long postNo) {
        return postRepository.delete(postNo);
    }
 */
    // 2023-01-17) JPA 연결하면서 생긴 오류 해결
    public void delete(final Long postNo)
        throws IllegalArgumentException {
        postRepository.deleteById(postNo);
    }
}
