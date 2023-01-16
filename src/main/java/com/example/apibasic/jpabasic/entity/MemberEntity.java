// 2023-01-16
package com.example.apibasic.jpabasic.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="userId")
@Builder

// JPA
@Entity     // JPA 의 Entity 객체
@Table(name="tbl_member")      // 테이블 명 변경 (default : 클래스 이름으로 - member_entity)
public class MemberEntity {

    @Id     // 기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 기본키 생성 전략
    @Column(name="user_code")    // 필드 명 변경
    private Long userId;        // 회원 식별 코드 (기본키)

    @Column(nullable = false, unique = true, length = 30)   // NOT NULL 제약조건, UNIQUE 제약조건(중복 방지), 길이 변경         * PK : NOT NULL, UNIQUE 모두 가지고있음
    private String account;     // 계정명

    @Column(nullable = false)
    private String password;    // 패스워드

    @Column(name = "user_nick", nullable = false)
    private String nickName;    // 닉네임

    // enum 에 Enumerated(EnumType.ORDINAL) 이 기본적으로 존재함
    @Enumerated(EnumType.STRING)       // ORDINAL : 순번(0부터 시작), STRING : 순수문자열
    private Gender gender;      // 성별

    @CreationTimestamp      // INSERT 시점에 서버시간을 자동으로 입력
    private LocalDateTime joinDate;     // 가입일자와 시간

    @UpdateTimestamp        // Update 시점에 서버시간을 자동으로 입력
    private LocalDateTime modifyDate;   // 정보 수정 시간
}
