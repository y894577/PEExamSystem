package com.scnu.peexamsystem.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MainConfiguration implements WebMvcConfigurer {

//.03

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/student/uploadFile/**")
                .addResourceLocations("classpath:/uploadFile/*");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/templates/")
                .addResourceLocations("classpath:/static/css")
                .addResourceLocations("classpath:/static/js")
                .addResourceLocations("classpath:/resources/uploadFile/*");
        registry.addResourceHandler("/student/uploadFile/**")
                .addResourceLocations("classpath:/resources/uploadFile/");
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
