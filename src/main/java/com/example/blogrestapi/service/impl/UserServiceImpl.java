package com.example.blogrestapi.service.impl;

import com.example.blogrestapi.entity.Role;
import com.example.blogrestapi.entity.User;
import com.example.blogrestapi.exception.BlogAPIException;
import com.example.blogrestapi.exception.InvalidCredentialError;
import com.example.blogrestapi.repository.RoleRepository;
import com.example.blogrestapi.repository.UserRepository;
import com.example.blogrestapi.security.JwtTokenProvider;
import com.example.blogrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public String register(String email,String name,String password,String userName) {

        if (userRepository.existsUserByEmail(email)){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Email already exists.");
        }else {
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName("USER").get();
            roles.add(userRole);

            User user = new User(
                    email,name,passwordEncoder.encode(password),userName,
                    roles
            );
            userRepository.save(user);
        }

        return "Register Successfully";
    }

    @Override
    public String login(String email, String password) {

        var user = userRepository.findUserByEmail(email)
                .orElseThrow(InvalidCredentialError::new);

        if ( passwordEncoder.matches(password,user.getPassword())  ){
            String token = tokenProvider.generateToken(email);
            return token;
        } else {
            throw new InvalidCredentialError();
        }
    }
}
