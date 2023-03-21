package com.example.blogrestapi.repository;

import com.example.blogrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post,Long> {

    public List<Post> findAllByCategory_Id(Long id);
}
