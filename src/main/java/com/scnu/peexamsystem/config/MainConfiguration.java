package com.scnu.peexamsystem.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MainConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new LoginHandlerInterception());
        registration.addPathPatterns("/*.html");
        registration.excludePathPatterns("/upload.html")
                .excludePathPatterns("/studentLogin.html")
                .excludePathPatterns("/adminLogin.html")
                .excludePathPatterns("/studentRegister.html")
                .excludePathPatterns("/js/axios.min.js")
                .excludePathPatterns("/**/*.js")
                .excludePathPatterns("/**/*.css");
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
