package com.example.blogrestapi.controller;

import com.example.blogrestapi.entity.Post;
import com.example.blogrestapi.payload.PostDto;
import com.example.blogrestapi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.blogrestapi.utils.AppConstants.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@Tag(
        name = "CRUD REST API for post"
)
public class PostController {

    @Autowired
    private PostService postService;

    @SecurityRequirement( name = "Bear Authentication" )
    @Operation( summary = "To create post",
                description = "Post is saved into database")
    @ApiResponse( responseCode = "201",
                  description = "HTTP created code")
    @PostMapping("/") public ResponseEntity<PostDto> create(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //http://localhost:8080/api/post/posts?pageSize=5&pageNo=0&sortBy=id&sortDir=desc
    @GetMapping("/posts")
    public List<PostDto> getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = DEFAULT_SORT_DIRECTION,required = false) String sortDir
    ){
        return postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable("id")Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,
                                              @PathVariable Long id){
        PostDto response = postService.updatePost(postDto,id);
        return new  ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable("id")Long id){
        postService.deletePost(id);
    }

    @GetMapping("/catId/{id}")
    public List<PostDto> getAllPostByCategory(@PathVariable Long id){
        return postService.getAllPostById(id);
    }
}
