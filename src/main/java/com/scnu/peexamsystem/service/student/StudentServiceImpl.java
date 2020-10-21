package com.scnu.peexamsystem.service.student;

import com.scnu.peexamsystem.dao.student.StudentDao;
import com.scnu.peexamsystem.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentDao studentDao;

    @Override
    public Map<String, Object> queryStudent(String stuName, String instituteNo, String classNo, String grade, String status) {

        //构建动态查询
        Specification<Student> specification = new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //用于暂时存放查询条件的集合
                List<Predicate> predicatesList = new ArrayList<>();

                if (!StringUtils.isEmpty(stuName)) {
                    predicatesList.add(criteriaBuilder.like(root.get("name"), stuName));
                }
                if (!StringUtils.isEmpty(instituteNo)) {
                    predicatesList.add(criteriaBuilder.equal(root.get("instituteNo"), instituteNo));
                }
                if (!StringUtils.isEmpty(classNo)) {
                    predicatesList.add(criteriaBuilder.equal(root.get("classNo"), instituteNo));
                }
                //最终将查询条件拼好然后return
                Predicate[] predicates = new Predicate[predicatesList.size()];
                Predicate and = criteriaBuilder.and(predicatesList.toArray(predicates));
                return and;
            }
        };
        List<Student> studentList = studentDao.findAll(specification);
        List<Student> list = studentDao.findAll(specification);
        for (Student student : list) {
            System.out.println(student);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("result", list.toArray());
        return map;
    }
}
