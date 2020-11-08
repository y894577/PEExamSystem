package com.scnu.peexamsystem.service.classes;

import com.scnu.peexamsystem.dao.classes.ClassDao;
import com.scnu.peexamsystem.entity.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Magic Gunner
 * @version 1.0
 */
@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    ClassDao classDao;

    /**
     * 获取年级列表
     * @return msg 返回消息
     *         code 状态码，1成功，0失败
     *         result 年级列表
     */
    public Map<String, Object> getGradeList() {
        Map<String, Object> map = new HashMap<>();
        List<String> grade = new ArrayList<>();
        ClassPathResource classPathResource = new ClassPathResource("static/grade.txt");
        try {
            InputStream inputStream = classPathResource.getInputStream();
            Scanner s = new Scanner(inputStream).useDelimiter(",");
            while (s.hasNext())
                grade.add(s.next());
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean isGet = !grade.isEmpty();

        map.put("msg", "获取grade" + (isGet ? "成功" : "失败"));
        map.put("result", grade);
        map.put("code", isGet ? 1 : 0);

        return map;
    }

    /**
     * 获取班级列表
     * @param instituteNo 学院号
     * @param grade 年级
     * @return msg 返回消息
     *         code 状态码，1成功，0失败
     *         result 班级列表
     */
    @Override
    public Map<String, Object> queryClassList(String instituteNo, String grade) {
        Map<String, Object> map = new HashMap<>();
        List<Class> list = classDao.findAllByInstituteNoAndGrade(instituteNo, grade);

        map.put("msg", "查询class" + "成功");
        map.put("result", list);
        map.put("code", 1);

        return map;
    }
}
