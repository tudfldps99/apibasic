// 2023-01-16
package com.example.apibasic.jpabasic.repository;

import com.example.apibasic.jpabasic.entity.Gender;
import com.example.apibasic.jpabasic.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

// JPA 로 CRUD Operation 을 하려면 JpaRepository 인터페이스를 상속
// 제너릭타입으로 첫번째로 CRUD 할 엔터티클래스의 타입, 두번째로 해당 엔터티의 Id의 타입 -- JpaRepository<T, ID>
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    // 2023-01-17
    // 조건이 있는 쿼리 메서드 (JPA)
    // 메서드 이름에 규칙 존재  --> https://docs.spring.io/spring-data/jpa/docs/2.7.7/reference/html/#jpa.query-methods.query-creation 참고
    List<MemberEntity> findByGender(Gender gender);     // Test : MemberRepositoryTest.java

    List<MemberEntity> findByAccountAndGender(String account, Gender gender);       // account 가 unique 제약 조건 걸려있기 때문에 굳이 List 로 받아올 필요 없음

    List<MemberEntity> findByNickNameContaining(String nickName);

    // -----------------------------------------------
    // JPQL 사용
    // 메서드 이름에 규칙 미존재
    // select 별칭 from 엔터티클래스명 as 별칭 where 별칭.필드명
    //  - native-sql : select m.user_code from tbl_member as m
    //  - jpql : select m.userId from MemberEntity as m

    // 계정명으로 회원 조회
    @Query("select m from MemberEntity as m where m.account =:acc")     //    @Query("select m from MemberEntity as m where m.account = ?1") 으로 작성 가능 (where 조건)
    MemberEntity getMemberByAccount(@Param("acc") String acc);            // 쿼리의 acc 와 매개변수의 acc 이름 동일해야 함

    // 닉네임과 성별 동시만족 조건으로 회원 조회
//    @Query("select m from MemberEntity m where m.nickName=?1 and m.gender=?2")     // db 에는 user_nick 으로 되어있지만 필드명은 nickName (MemberEntity.java 에서 확인 가능)
//    List<MemberEntity> getMemberByNickAndGender(String nickName, Gender gender);
    @Query("select m from MemberEntity m where m.nickName=:nickName and m.gender=:gen")     // 숫자 방식보다는 문자 방식의 처리를 더 선호 why? 파라미터의 순서가 바뀌어도 쿼리 변경할 필요 없음
    List<MemberEntity> getMemberByNickAndGender(@Param("nickName") String nickName, @Param("gen") Gender gen);

    @Query("select m from MemberEntity m where m.nickName like %:nick%")
    List<MemberEntity> getMembersByNickName(@Param("nick") String nick);

    @Transactional  // 데이터 조회 -> 삭제 -> 조회 과정에서 데이터를 삭제 하는 도중 에러 발생 시 다시 롤백되도록 하는 어노테이션
    @Modifying      // 수정, 삭제할 때 붙이기
    @Query("delete from MemberEntity m where m.nickName=:nick")
    void deleteByNickName(@Param("nick")String nick);
}
