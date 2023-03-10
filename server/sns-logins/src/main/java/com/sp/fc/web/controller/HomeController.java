package com.sp.fc.web.controller;

import com.sp.fc.user.domain.SpUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    //인증받은자만 들어올 수 있음 //SnsLoginSecurityConfig
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/")
    public Object greeting(@AuthenticationPrincipal Object user){
        return user;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public SpUser greeting(@AuthenticationPrincipal SpUser user){
        return user;
    }
}
