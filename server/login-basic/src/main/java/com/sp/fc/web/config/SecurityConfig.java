package com.sp.fc.web.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthDetail customAuthDetail;

    public SecurityConfig(CustomAuthDetail customAuthDetail) {
        this.customAuthDetail = customAuthDetail;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser(
                        User.withDefaultPasswordEncoder()
                                .username("user1")
                                .password("1111")
                                .roles("USER")
                ).withUser(
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("2222")
                        .roles("ADMIN")
        );
    }

    //관리자가 유저페이지에도 접근할 수 있다
    @Bean
    RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(request->
                    request.antMatchers("/").permitAll() // --루트페이지 모든 사람 허용
                            .anyRequest().authenticated()
                )
                .formLogin(login-> // --폼로그인에 로그인페이지 설정
                        login.loginPage("/login")
                        .loginProcessingUrl("/loginprocess")
                        .permitAll()
                        .defaultSuccessUrl("/", false) //로그인 후 메인페이지, false로 해야 해당 버튼누른후로그인되면 그곳으로 돌아감
                        .authenticationDetailsSource(customAuthDetail) //세부사항 확인(시간,ip .. )
                        .failureUrl("/login-error")
                )
                .logout(logout-> // -- 로그아웃시 루트페이지로
                        logout.logoutSuccessUrl("/"))
                .exceptionHandling(error-> //-- user와 admin 구분
                        error.accessDeniedPage("/access-denied")
                )
                ;
    }

    //static 디렉토리 시큐리티 무시
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations()
                )
        ;
    }

}
