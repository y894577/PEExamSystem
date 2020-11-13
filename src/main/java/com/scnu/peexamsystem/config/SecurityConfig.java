package com.scnu.peexamsystem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scnu.peexamsystem.entity.Student;
import com.scnu.peexamsystem.service.admin.AdminServiceImpl;
import com.scnu.peexamsystem.service.student.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.util.DigestUtils;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private AdminServiceImpl adminService;

    /**
     * 指定加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用BCrypt加密密码
        return new MessageDigestPasswordEncoder("MD5");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/error/**", "/static/**", "/**/**.js", "/js/**", "/**/**.css", "/*Register.html");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        http.authorizeRequests().antMatchers("/**/login")
                .permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated();

        http.formLogin().
                loginPage("/studentLogin.html")
                .loginProcessingUrl("/student/login")
                .usernameParameter("stuNo")
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler())
                .permitAll();

        http.formLogin()
                .loginPage("/adminLogin.html")
                .loginProcessingUrl("/admin/login")
                .usernameParameter("adminID")
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler())
                .permitAll();


        http.formLogin().and()
                .logout()
                .logoutUrl("/**/logout")
                .logoutSuccessUrl("/studentLogin.html")
                .permitAll();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(studentService)
                .passwordEncoder(passwordEncoder());
        auth.userDetailsService(adminService)
                .passwordEncoder(passwordEncoder());
    }
}
