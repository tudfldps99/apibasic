package com.example.apibasic.jpabasic.repository;

// junit5에서는 클래스, 메서드, 필드 default 제한만을 허용

import com.example.apibasic.jpabasic.entity.Gender;
import com.example.apibasic.jpabasic.entity.MemberEntity;
import org.junit.jupiter.api.BeforeEach;
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

    // @BeforeEach - 각 테스트를 실행하기 전에 실행되는 내용
    @BeforeEach
    void bulkInsert() {
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
        MemberEntity saveMember4 = MemberEntity.builder()
                .account("ddd25")
                .password("11111")
                .nickName("김달고나")
                .gender(FEMALE)
                .build();
        MemberEntity saveMember5 = MemberEntity.builder()
                .account("f33351")
                .password("6656")
                .nickName("박바보")
                .gender(FEMALE)
                .build();

        memberRepository.save(saveMember1);
        memberRepository.save(saveMember2);
        memberRepository.save(saveMember3);
        memberRepository.save(saveMember4);
        memberRepository.save(saveMember5);
    }

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

        Optional<MemberEntity> foundMember = memberRepository.findById(1L);     // pk 기반 단일 행 조회

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
        /* --> BeforeEach
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
        */

        // when
        /*
        memberRepository.save(saveMember1);
        memberRepository.save(saveMember2);
        memberRepository.save(saveMember3);
        */
        List<MemberEntity> memberEntityList = memberRepository.findAll();       // select 쿼리 실행

        //then
        // 조회된 리스트의 사이즈는 3이어야 한다.
        assertEquals(3, memberEntityList.size());

        // 조회된 리스트의 두번째 회원 닉네임은 궁예이어야 한다.
        assertEquals("궁예", memberEntityList.get(1).getNickName());

        System.out.println("\n==================================");
        memberEntityList.forEach(System.out::println);
        System.out.println("==================================");
    }

    // 삭제 테스트
    @Test
    @DisplayName("회원 데이터를 3개 등록하고, 그 중 하나의 회원을 삭제해야 한다.")
    @Transactional
    @Rollback
    void deleteTest() {
        // given    --> BeforeEach
        Long userCode = 2L;

        // when
        memberRepository.deleteById(userCode);      // delete 쿼리 실행
        Optional<MemberEntity> foundMember = memberRepository.findById(userCode);       // select 쿼리 실행

        //then
        // 1)
        assertFalse(foundMember.isPresent());       // isPresent : 현재 존재하는지의 여부
        // 2)
        assertEquals(2, memberRepository.findAll().size());
    }

    // 수정 테스트
    @Test
    @DisplayName("2번 회원의 닉네임과 성별을 수정해야 한다.")
    @Transactional
    @Rollback
    void modifyTest() {
        // given
        Long userCode = 2L;
        String newNickName = "닭강정";
        Gender newGender = FEMALE;

        // when
        // JPA 에서 수정은 조회 후 setter 로 변경 후 다시 save
        Optional<MemberEntity> foundMember = memberRepository.findById(userCode);

        /*
        if (foundMember.isPresent()) {
            MemberEntity m = foundMember.get();
            m.setNickName(newNickName);
            m.setGender(newGender);
            memberRepository.save(m);
        }
        */

        foundMember.ifPresent(m -> {         // ifPresent : 데이터가 만약 존재하면  ( isPresent 를 if 문으로 검사한 것 (173~178)
            m.setNickName(newNickName);
            m.setGender(newGender);
            memberRepository.save(m);
        });
        // 수정 후 조회
        Optional<MemberEntity> modifiedMember = memberRepository.findById(userCode);

        // then
        assertEquals("닭강정", modifiedMember.get().getNickName());
        assertEquals(FEMALE, modifiedMember.get().getGender());
    }

    // 2023-01-17
    @Test
    @DisplayName("쿼리메서드를 사용하여 여성회원만 조회해야 한다")
    void findByGenderTest() {
        // given
        Gender gender = FEMALE;

        // when
        List<MemberEntity> list = memberRepository.findByGender(gender);

        // then
        list.forEach(m -> {
            System.out.println(m);
            assertTrue(m.getGender() == FEMALE);
        });
    }

    @Test
    @DisplayName("쿼리메서드를 사용하여 계정명이 'abc4321'이면서 남성인 회원만 조회해야 한다")
    void findByAccountAndGenderTest() {
        // given
        String account = "abc4321";
        Gender gender = MALE;

        // when
        List<MemberEntity> list = memberRepository.findByAccountAndGender(account, gender);

        // then
        list.forEach(m -> {
            System.out.println(m);
            assertTrue(m.getGender() == MALE);
            assertEquals("abc4321", m.getAccount());
        });
    }

    @Test
    @DisplayName("쿼리메서드를 사용하여 닉네임에 '박' 이 포함된 회원만 조회해야 한다")
    void findByNickNameContaining() {
        // given
        String nickName = "박";

        // when
        List<MemberEntity> list = memberRepository.findByNickNameContaining(nickName);

        // then
        list.forEach(m -> {
            System.out.println(m);
            assertTrue(m.getNickName().contains("박"));
        });
    }

    // JPQL
    @Test
    @DisplayName("JPQL 을 사용해서 계정명이 zzz1234 인 회원을 조회해야 한다.")
    void jpqlTest1() {
        // given
        String account = "zzz1234";

        // when
        MemberEntity member = memberRepository.getMemberByAccount(account);

        // then
        System.out.println("member = " + member);
        assertEquals("꾸러긔", member.getNickName());
    }

    @Test
    @DisplayName("JPQL 을 사용해서 닉네임이 궁예이면서 성별이 남성인 회원을 조회해야 한다.")
    void jpqlTest2() {
        // given
        String nickName = "궁예";
        Gender gender = MALE;

        // when
        List<MemberEntity> list = memberRepository.getMemberByNickAndGender(nickName, gender);

        // then
        list.forEach(m -> {
            System.out.println(m);
            assertEquals("궁예", m.getNickName());
            assertEquals(MALE, m.getGender());
        });
    }

    @Test
    @DisplayName("JPQL 을 사용해서 이름에 '박' 이 포함된 회원만 조회해야 한다.")
    void jpqlTest3() {
        // given
        String nickName = "박";

        // when
        List<MemberEntity> list = memberRepository.getMembersByNickName(nickName);

        // then
        list.forEach(m -> {
            System.out.println(m);
            assertTrue(m.getNickName().contains("박"));
        });
    }

    @Test
    @DisplayName("JPQL 을 사용해서 이름에 '박바보'인 회원을 삭제해야 한다.")
    void jpqlTest4() {
        // given
        String nickName = "박바보";

        // when
        memberRepository.deleteByNickName(nickName);

        // then
        List<MemberEntity> list =  memberRepository.findAll();
        list.forEach(System.out::println);
    }
}