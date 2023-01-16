package com.example.apibasic.jpabasic.repository;

// junit5에서는 클래스, 메서드, 필드 default 제한만을 허용

import com.example.apibasic.jpabasic.entity.MemberEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.example.apibasic.jpabasic.entity.Gender.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  // 스프링 컨테이너를 사용해서 스프링이 관리하는 객체를 주입받는 기능
class MemberRepositoryTest {

    // 스프링 빈을 주입받을 때 필드주입을 사용
    @Autowired
    MemberRepository memberRepository;

    // 테스트 메서드
    // 테스트는 여러번 돌려도 성공한 테스트는 계속 성공해야 한다
    // 단언 (Assertion) : 강력히 주장한다.
    @Test
    @DisplayName("회원의 가입 정보를 데이터베이스에 저장해야 한다.")
    @Transactional
    @Rollback       // 테스트가 끝나면 롤백해라 (지워라)
    void saveTest() {
        // given - when - then 패턴
        // given : 테스트시 주어지는 데이터
        MemberEntity saveMember = MemberEntity.builder()
                .account("zzz1234")
                .password("1234")
                .nickName("꾸러긔")
                .gender(FEMALE)
                .build();

        // when : 실제 테스트 상황
        memberRepository.save(saveMember);  // insert 쿼리 실행

        Optional<MemberEntity> foundMember = memberRepository.findById(1L);// pk 기반 단일 행 조회

        // then : 테스트 결과 단언
        // 회원이 조회되었을 것이다.
        MemberEntity member = foundMember.get();
        assertNotNull(member);

        // 그 저장된 회원의 user_code 는 몇번? => 1번
        // param1: 내 기대값,  param2: 실제값
//        assertEquals(1L, member.getUserId());     --> user_code 값이 계속 증가하기 때문에 이런 단언은 좋지 않음

        // 저장된 회원의 닉네임은 뭘까요?  => 꾸러긔
        assertEquals("꾸러긔", member.getNickName());
    }

    // 목록 조회 테스트
    @Test
    @DisplayName("회원 목록을 조회하면 3명의 회원정보가 조회되어야 한다.")
    @Transactional
    @Rollback
    void findAllTest() {
        // given
        MemberEntity saveMember1 = MemberEntity.builder()
                .account("zzz1234")
                .password("1234")
                .nickName("꾸러긔")
                .gender(FEMALE)
                .build();
        MemberEntity saveMember2 = MemberEntity.builder()
                .account("abc4321")
                .password("4321")
                .nickName("궁예")
                .gender(MALE)
                .build();
        MemberEntity saveMember3 = MemberEntity.builder()
                .account("ppp9999")
                .password("9988")
                .nickName("찬호박")
                .gender(MALE)
                .build();

        // when
        memberRepository.save(saveMember1);
        memberRepository.save(saveMember2);
        memberRepository.save(saveMember3);

        List<MemberEntity> memberEntityList = memberRepository.findAll();

        //then
        // 조회된 리스트의 사이즈는 3이어야 한다.
        assertEquals(3, memberEntityList.size());

        // 조회된 리스트의 두번째 회원 닉네임은 궁예이어야 한다.
        assertEquals("궁예", memberEntityList.get(1).getNickName());

        System.out.println("\n==================================");
        memberEntityList.forEach(System.out::println);
        System.out.println("==================================");
    }
}