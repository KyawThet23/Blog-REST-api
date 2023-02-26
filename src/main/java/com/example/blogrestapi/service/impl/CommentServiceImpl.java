package com.example.blogrestapi.service.impl;

import com.example.blogrestapi.entity.Comment;
import com.example.blogrestapi.entity.Post;
import com.example.blogrestapi.exception.BlogAPIException;
import com.example.blogrestapi.exception.ResourceNotFoundException;
import com.example.blogrestapi.payload.CommentDto;
import com.example.blogrestapi.repository.CommentRepository;
import com.example.blogrestapi.repository.PostRepository;
import com.example.blogrestapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;


    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {

        //Dto to entity
        Comment comment = dtoToEntity(commentDto);

        //Set post
        comment.setPost(postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post","id",postId)));

        commentRepository.save(comment);

        return commentDto;
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(Long postId) {

        List<Comment> comments = commentRepository.findAllByPostId(postId);

        return comments.stream()
                .map(comment -> entityToDto(comment))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {

        //Post
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));

        //comment
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"The comment doesn't belongs to post.");
        }else {
            return entityToDto(comment);
        }

    }

    @Override
    public CommentDto updateComment(Long cmtId, Long postId, CommentDto commentDto) {

        //Post
       Post post = postRepository.findById(postId)
               .orElseThrow(() -> new ResourceNotFoundException("Comment","id",postId));

        //comment
        Comment existingComment = commentRepository.findById(cmtId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment","id",cmtId));

        if (!existingComment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"The comment doesn't belongs to post.");
        }else {


            existingComment.setBody(commentDto.getBody() != null ?
                    commentDto.getBody() : existingComment.getBody());

            commentRepository.save(existingComment);

            return entityToDto(existingComment);
        }
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }else {
            commentRepository.delete(comment);
        }

    }

    private Comment dtoToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(comment.getId());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        return comment;
    }
    private CommentDto entityToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setEmail(comment.getEmail());
        commentDto.setName(comment.getName());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }
}
