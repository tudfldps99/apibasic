// 2023-01-13
package com.example.apibasic.post.repository;

import com.example.apibasic.post.entity.PostEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;

// 게시물 데이터를 CRUD (생성, 조회, 수정, 삭제)
//@Component      // 이 클래스로 만든 객체는 스프링이 관리좀 해줘라 (객체의 생성/유지/사망/해제 등)
@Repository     // bean 등록을 하면서 저장소 개념까지 포함 (@Repository 가 @Component 포함)
public class PostRepository {       /* 주방장 */

    // 메모리 저장소
    private static final Map<Long, PostEntity> posts = new HashMap<>();        // key: 게시물 번호

    // 게시물 목록 조회
    public List<PostEntity> findAll() {
        List<PostEntity> postEntityList = new ArrayList<>();

        Set<Long> keySet = posts.keySet();      // 키 값만 다 뽑아서 줌
        for (Long postNo : keySet) {
            PostEntity postEntity = posts.get(postNo);
            postEntityList.add(postEntity);
        }
        return postEntityList;
    }

    // 게시물 개별 조회
    public PostEntity findOne(Long postNo) {
        return posts.get(postNo);
    }

    // 게시물 등록, 게시물 수정 (잘 등록했는지 안했는지만 확인)
    // Map 의 특성 때문) 존재하지 않는 키면 키 등록이 됨, 기존에 존재하는 키 값이면 수정이 됨
    public boolean save(PostEntity postEntity) {
        PostEntity post = posts.put(postEntity.getPostNo(), postEntity);
        //return post != null;            // null 이 아니면 잘 들어온 것
        return true;
    }

    // 게시물 삭제
    public boolean delete(Long postNo) {
        PostEntity remove = posts.remove(postNo);
        return remove != null;
    }
}