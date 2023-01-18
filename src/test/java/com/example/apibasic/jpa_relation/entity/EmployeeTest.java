// 2023-01-18
package com.example.apibasic.jpa_relation.entity;

import com.example.apibasic.jpa_relation.repository.DepartmentRepository;
import com.example.apibasic.jpa_relation.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class EmployeeTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    void empTest1() {
        Department dept1 = Department.builder()
                .deptName("개발부")
                .build();

        Department dept2 = Department.builder()
                .deptName("영업부")
                .build();

        Employee emp1 = Employee.builder()
                .empName("푸파파파")
                .department(dept1)      // 부서 정보 전체를 넣음
                .build();

        Employee emp2 = Employee.builder()
                .empName("헬로키티")
                .department(dept1)      // 부서 정보 전체를 넣음
                .build();

        departmentRepository.save(dept1);
        departmentRepository.save(dept2);

        employeeRepository.save(emp1);
        employeeRepository.save(emp2);
    }
    
    @Test
    @Transactional      // 트랜잭션 : All of Nothing (전체 성공이 아니면 의미 없음)
    void empTest2() {
        Employee foundEmp
                = employeeRepository.findById(1L)     // SELECT 문
                .orElseThrow();

        System.out.println("foundEmp = " + foundEmp.getEmpName());
    }

    @Test
    @Transactional
    void empTest3() {
        Department dept
                = departmentRepository.findById(1L)
                .orElseThrow();

        List<Employee> employees = dept.getEmployees();
        employees.forEach(System.out::println);

    }
}