package com.scnu.peexamsystem.config;

import com.scnu.peexamsystem.service.admin.AdminServiceImpl;
import com.scnu.peexamsystem.service.student.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Configuration
    @Order(2)
    static class StudentSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private StudentServiceImpl studentService;

        @Bean
        public PasswordEncoder passwordEncoder() {
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

            http.authorizeRequests().antMatchers("/student/login", "/institute/**", "/class/**").permitAll()
                    .antMatchers("/student/studentDetail.html").hasAuthority("STUDENT_IS_SUBMIT")
                    .antMatchers("/student/studentSubmit.html").hasAuthority("STUDENT_NOT_SUBMIT")
                    .anyRequest().authenticated();

            http.formLogin().
                    loginPage("/studentLogin.html")
                    .loginProcessingUrl("/student/login")
                    .usernameParameter("stuNo")
                    .successHandler(new LoginHandler.LoginSuccessHandler())
                    .failureHandler(new LoginHandler.LoginFailureHandler())
                    .permitAll();


            http.formLogin().and()
                    .logout()
                    .logoutUrl("/student/logout")
                    .logoutSuccessHandler(new LoginHandler.LogoutHandler())
                    .logoutSuccessUrl("/studentLogin.html")
                    .permitAll();

        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(studentService)
                    .passwordEncoder(passwordEncoder());
        }
    }

    @Configuration
    @Order(3)
    static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private AdminServiceImpl adminService;

        @Bean
        public PasswordEncoder passwordEncoder() {
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

            http.authorizeRequests().antMatchers("/admin/login").permitAll()
                    .antMatchers("/admin/**").hasAuthority("ADMIN");

            http.formLogin().
                    loginPage("/adminLogin.html")
                    .loginProcessingUrl("/admin/login")
                    .usernameParameter("adminID")
                    .successHandler(new LoginHandler.LoginSuccessHandler())
                    .failureHandler(new LoginHandler.LoginFailureHandler())
                    .permitAll();


            http.formLogin().and()
                    .logout()
                    .logoutUrl("/admin/logout")
                    .logoutSuccessHandler(new LoginHandler.LogoutHandler())
                    .logoutSuccessUrl("/admin/adminLogin.html")
                    .permitAll();

        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(adminService)
                    .passwordEncoder(passwordEncoder());
        }
    }
}
