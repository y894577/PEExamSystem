package com.scnu.peexamsystem.controller;

import com.mysql.cj.protocol.x.Notice;
import com.scnu.peexamsystem.entity.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<String> response;
    HttpHeaders headers = new HttpHeaders();
    private HttpEntity<MultiValueMap<String, Object>> request;
    private MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    private final String basicUrl = "http://localhost:8001/student";
    private String url;
    private String method;


    @BeforeEach
    public void before() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("---------------执行前---------------");
        System.out.println();
    }

    @Test
    public void modifyStatus() {
        url = "/verify";
        map.add("stuNo", "20182005001");
        map.add("status", "1");
        method = "post";
    }

    @Test
    public void queryStudent() {
        url = "/query/single?stuNo={stuNo}";
        map.add("stuNo", "20182005001");
        method = "get";
    }


    @Test
    public void queryStudentList() {
        url = "/query/list?queryStatus={queryStatus}&currentPageNo={currentPageNo}&pageSize={pageSize}";
        map.add("queryStatus", "1");
        map.add("currentPageNo", 1);
        map.add("pageSize", 10);
        method = "get";
    }

    @Test
    public void studentLogin() {
        url = "/login";
        map.add("stuNo", "20182005001");
        map.add("password", "123456");
        method = "post";
    }

    @Test
    public void studentRegister() {
        url = "/register";
        map.add("stuNo", "20182005003");
        map.add("password", "123456");
        method = "post";
    }

    @Test
    public void studentLogout(){
        url = "/logout";
        map.add("stuNo", "20182005001");
        method = "post";
    }

    @Test
    public void submitApplication(){
        url = "/submit";
        Student student = new Student();
        student.setStuNo("20182005004");
        student.setStuName("小智");
        student.setGender(1);
        student.setInstituteNo("RJXY");
        student.setClassNo("RJGC1805");
        student.setSubmitReason("不知道");
        student.setMedicalRecord("aaaa");
        map.add("student", student);
        method = "post";
    }


    @AfterEach
    public void after() {
        url = basicUrl + url;
        request = new HttpEntity<>(map);
        switch (method) {
            case "post":
                response = restTemplate.postForEntity(url, request, String.class);
                break;
            case "get":
                response = restTemplate.getForEntity(url, String.class, map);
                break;
        }

        System.out.println("[URL]    " + url);
        System.out.println();
        System.out.println("[REQUEST]    " + request);
        System.out.println();
        System.out.println("[STATUS]     " + response.getStatusCode());
        System.out.println();
        System.out.println("[RESPONSE]   " + response.getBody());
        System.out.println();
        System.out.println("---------------执行后---------------");
    }
}