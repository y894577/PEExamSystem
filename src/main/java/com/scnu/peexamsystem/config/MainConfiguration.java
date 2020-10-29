package com.scnu.peexamsystem.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MainConfiguration implements WebMvcConfigurer {
    @Value("${upload.file.path}")
    private String filePath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration loginRegistration = registry.addInterceptor(new LoginHandlerInterception());
        InterceptorRegistration studentRegistration = registry.addInterceptor(new StudentHandlerInterception());
        InterceptorRegistration adminRegistration = registry.addInterceptor(new AdminHandlerInterception());

        loginRegistration.addPathPatterns("/**");
        loginRegistration.excludePathPatterns("/error/*")
                .excludePathPatterns("/js/axios.min.js")
                .excludePathPatterns("/**/*.js")
                .excludePathPatterns("/**/*.css")
                .excludePathPatterns("/studentLogin.html")
                .excludePathPatterns("/studentRegister.html")
                .excludePathPatterns("/adminLogin.html")
                .excludePathPatterns("/student/login")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/student/logout")
                .excludePathPatterns("/student/register");

        studentRegistration.addPathPatterns("/**");
        studentRegistration.excludePathPatterns("/student/login")
                .excludePathPatterns("/student/register")
                .excludePathPatterns("/student/getStuNo")
                .excludePathPatterns("/student/logout")
                .excludePathPatterns("/student/uploadFile/**")
                .excludePathPatterns("/js/axios.min.js");

        adminRegistration.addPathPatterns("/adminLists.html")
                .addPathPatterns("/adminDetail.html")
                .excludePathPatterns("/adminLogin.html")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/logout")
                .excludePathPatterns("/student/uploadFile/**")
                .excludePathPatterns("/js/axios.min.js");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/student/uploadFile/**")
                .addResourceLocations("file:" + filePath);
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/templates/")
                .addResourceLocations("classpath:/static/css")
                .addResourceLocations("classpath:/static/js");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
