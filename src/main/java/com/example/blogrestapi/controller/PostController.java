package com.example.blogrestapi.controller;

import com.example.blogrestapi.payload.PostDto;
import com.example.blogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.blogrestapi.utils.AppConstants.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/")
    public ResponseEntity<PostDto> create(@RequestBody PostDto postDto){
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

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable("id")Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
                                              @PathVariable Long id){
        PostDto response = postService.updatePost(postDto,id);
        return new  ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id")Long id){
        postService.deletePost(id);
    }
}
