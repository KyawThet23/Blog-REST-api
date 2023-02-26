package com.example.blogrestapi.repository;

import com.example.blogrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post,Long> {
}
