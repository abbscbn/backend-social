package com.abbscoban.social.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abbscoban.social.ResDto.ResponseUserDto;
import com.abbscoban.social.jwt.AuthRequest;
import com.abbscoban.social.jwt.AuthResponse;
import com.abbscoban.social.jwt.RefleshTokenRequest;
import com.abbscoban.social.Service.AuthService;
import com.abbscoban.social.Service.RefleshTokenService;

@RestController
public class RestAuthController  {

    @Autowired
    private AuthService authService;

    @Autowired
    private RefleshTokenService refleshTokenService;


    @PostMapping("/register")
    public ResponseUserDto register(@RequestBody AuthRequest request) {

        return authService.register(request);
    }


    @PostMapping("/authenticate")
    public AuthResponse authenticate(@RequestBody AuthRequest request) {
        return authService.authenticate(request);

    }

    @PostMapping("/refleshtoken")
    public AuthResponse refleshToken(@RequestBody RefleshTokenRequest request) {
        return refleshTokenService.refleshToken(request);

    }



}