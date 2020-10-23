package com.scnu.peexamsystem.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MainConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration registration = registry.addInterceptor(new LoginHandlerInterception());
//        registration.addPathPatterns("/**");
//        registration.excludePathPatterns("/**/login.html");
    }
}
