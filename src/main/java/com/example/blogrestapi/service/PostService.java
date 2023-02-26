package com.example.blogrestapi.service;

import com.example.blogrestapi.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPost(int pageNo,int pageSize,String sortBy,String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto,Long id);
    void deletePost(Long id);
}
