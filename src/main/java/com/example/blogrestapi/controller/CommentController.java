package com.example.blogrestapi.controller;

import com.example.blogrestapi.payload.CommentDto;
import com.example.blogrestapi.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //http://localhost:8080/api/comment/create/4
    @PostMapping("/create/{id}")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto,
                                        @PathVariable("id")Long postId){

        return new ResponseEntity<>( commentService.createComment(postId,commentDto),
                HttpStatus.CREATED) ;
    }

    @GetMapping("/comments/{id}")
    public List<CommentDto> getCommentsByPostId(@PathVariable("id")Long postId){
        return commentService.getAllCommentsByPostId(postId);
    }

    @GetMapping("/{postId}/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("commentId")Long cmtId,
                                                     @PathVariable("postId")Long postId){

        CommentDto commentDto = commentService.getCommentById(postId,cmtId);

        return new  ResponseEntity<>(commentDto,HttpStatus.OK);
    }

    @PutMapping("/update/{commentId}/{postId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("commentId")Long cmtId,
            @PathVariable("postId")Long postId,
            @Valid @RequestBody CommentDto commentDto
    ){
        CommentDto commentDto1 = commentService.updateComment(cmtId,postId,commentDto);

        return ResponseEntity.ok(commentDto1);

    }

    @DeleteMapping("/delete/{id}/{postId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "id") Long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }
}
