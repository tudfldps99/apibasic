package com.example.apibasic.jpa_relation.repository;

import com.example.apibasic.jpa_relation.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
