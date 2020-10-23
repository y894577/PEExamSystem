package com.scnu.peexamsystem.controller;

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

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InstituteControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<String> response;
    HttpHeaders headers = new HttpHeaders();
    private HttpEntity<MultiValueMap<String, Object>> request;
    private MultiValueMap<String, Object> postMap = new LinkedMultiValueMap<>();
    private Map<String, Object> getMap = new HashMap<>();
    private final String basicUrl = "http://localhost:8001/institute";
    private String url;
    private String method;

    @BeforeEach
    public void before() {
        System.out.println("<<ClassController测试>>");
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("---------------执行前---------------");
    }

    @AfterEach
    public void after() {
        url = basicUrl + url;
        System.out.println("[URL]    " + url);
        System.out.println();

        request = new HttpEntity<>(postMap);
        switch (method) {
            case "post":
                response = restTemplate.postForEntity(url, request, String.class);
                System.out.println("[POST REQUEST]    " + request);
                break;
            case "get":
                response = restTemplate.getForEntity(url, String.class, getMap);
                System.out.println("[GET PARAMETER]    " + getMap.toString());
                break;
        }

        System.out.println();
        System.out.println("[STATUS]     " + response.getStatusCode());
        System.out.println();
        System.out.println("[RESPONSE]   " + response.getBody());
        System.out.println("---------------执行后---------------");
    }

    @Test
    public void queryInstituteList(){
        url = "/query";
        method = "get";
    }

}