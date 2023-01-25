package com.sp.fc.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true) //prePost로 권한 체크를 하겠다
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService userService(){
        final PasswordEncoder pw = passwordEncoder();
        UserDetails user2 = User.builder()
                .username("user2")
                .password(pw.encode("1234"))
                .roles("USER").build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(pw.encode("1234"))
                .roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user2, admin);
    }

    //비밀번호 encoder
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //홈페이지 경우 누구나 접근할 수 있게 -> 인증(로그인) 없이
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .and()
                .authorizeRequests(auth->{
                    auth.anyRequest().permitAll()
                    ;
                })
        ;
    }
}
