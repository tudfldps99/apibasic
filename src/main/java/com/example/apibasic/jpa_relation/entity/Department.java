// 2023-01-18
package com.example.apibasic.jpa_relation.entity;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter
@ToString(exclude = "employees")        // Department 안에 Employee 정보 있고, Employee 안에 Department 정보 있어서 무한호출되어 StackOverflowError 발생되기 때문에 List 인 employees 는 ToString 에서 제외
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "deptId")
@Builder
@Entity
public class Department {       // 한 개의 부서(Department, 1) 당 사원(Employee, M) 여러명

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptId;        // 부서번호 (PK)

    private String deptName;    // 부서명

    // 객체지향세계에서는 가능 (양방향매핑)
    // 양방향매핑에서는 상대방 엔터티의 정보를 수정할 수는 없고, 단순 읽기(조회)만 지원한다.
    @OneToMany(mappedBy = "department")     // mappedBy 에는 상대방 엔터티의 조인되는 필드명을 쓴다. (Employee.java 에서 확인)     ==> 선택
    private List<Employee> employees = new ArrayList<>();

    /*
      * ManyToOne (1대 M(다) 중 M) 은 필수로 작성, OneToMany 는 선택
     */
}
