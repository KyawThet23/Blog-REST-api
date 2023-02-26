package com.example.blogrestapi.service;

import com.example.blogrestapi.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long postId, CommentDto commentDto);
    List<CommentDto> getAllCommentsByPostId(Long postId);
    CommentDto getCommentById(Long postId,Long commentId);
    CommentDto updateComment(Long cmtId,Long postId,CommentDto commentDto);
    void deleteComment(Long cmtId,Long postId);
}
