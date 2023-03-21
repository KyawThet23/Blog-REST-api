package com.example.blogrestapi.service;

public interface UserService {

    String register(String email,String name,
                    String password,String userName);

    String login(String email, String password);
}
