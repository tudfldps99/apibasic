// 2023-01-18
package com.example.apibasic.jpa_relation.entity;

import lombok.*;
import javax.persistence.*;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "empId")
@Builder
@Entity
public class Employee {     // 한 개의 부서(Department, 1) 당 사원(Employee, M) 여러명

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId;     // 사원번호 (PK)

    private String empName;     // 사원명

    // - 1) SQL 적 개념 (정규화 신경)
    //private Long deptId;        // 부서코드 (FK)

    /*
        EAGER : 항상 조인을 하도록 설정
        LAZY : 부서 정보가 필요할 때만 조인 (실무에서는 ManyToOne 시 무조건 LAZY 로 설정)
     */
    @ManyToOne(fetch = FetchType.LAZY)  // 연관관계 매핑(M 쪽에 작성)      ==> 필수
    @JoinColumn(name = "dept_id")       // FK 컬럼 이름 지정
    // - 2) 객체지향적 개념  (JPA 도 객체지향적)
    private Department department;      // 모든 부서 정보
                                        // --> 그러나 이렇게 하면 항상 모든 상황에서 JOIN 하게 됨. 낭비
                                        // --> @ManyTone 에 (fetch = FetchType.EAGER) 가 생략되어있음. LAZY 로 설정 필요

    /*
     * ManyToOne (1대 M(다) 중 M) 은 필수로 작성, OneToMany 는 선택
    */
}
