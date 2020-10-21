package com.scnu.peexamsystem.dao.student;

import com.scnu.peexamsystem.entity.Student;
import jdk.nashorn.internal.runtime.Specialization;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student> {

    List<Student> findByStuNo(String stuNo);

    boolean existsByStuNoAndPassword(String stuNo, String password);

    List<Student> findAll(Specification<Student> specification);
}
