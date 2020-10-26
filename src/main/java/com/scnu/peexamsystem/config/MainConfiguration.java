package com.scnu.peexamsystem.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MainConfiguration implements WebMvcConfigurer {

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
                .excludePathPatterns("/student/register");

        studentRegistration.addPathPatterns("/**");
        studentRegistration.excludePathPatterns("/student/login")
                .excludePathPatterns("/student/register");

        adminRegistration.addPathPatterns("/adminLists.html")
                .excludePathPatterns("/adminLogin.html")
                .excludePathPatterns("/admin/login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/student/uploadFile/**")
                .addResourceLocations("classpath:/resources/uploadFile/");
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
