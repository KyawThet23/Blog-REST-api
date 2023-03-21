package com.example.blogrestapi.repository;

import com.example.blogrestapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    public Optional<User> findByNameAndEmail(String name,String email);

    public boolean existsUserByEmail(String email);

    public Optional<User> findUserByEmail(String email);
}
