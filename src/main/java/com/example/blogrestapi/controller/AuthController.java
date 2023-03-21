package com.example.blogrestapi.controller;

import com.example.blogrestapi.security.JwtTokenProvider;
import com.example.blogrestapi.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    record registerRequest(String email,
                           String name,
                           String password,
                           @JsonProperty("user_name") String userName){

    }
    record registerResponse(String name,
                            @JsonProperty("user_name") String userName,
                            String email){}

    // http://loclhost:8080/auth/register
    @PostMapping("/register")
    public registerResponse register(@RequestBody registerRequest register){
        userService.register(
                register.email(),register.name(),register.password(),register.userName()
        );
        return new registerResponse(
                register.name(), register.userName(), register.email()
        );
    }
    record loginRequest(String email,String password){}
    record loginResponse(String email,String token){}

    @PostMapping("/login")
    public loginResponse login(@RequestBody loginRequest request){

        String token = userService.login(request.email(),request.password());

        return new loginResponse(request.email(), token);
    }
}
