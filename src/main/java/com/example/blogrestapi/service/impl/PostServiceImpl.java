package com.example.blogrestapi.service.impl;

import com.example.blogrestapi.entity.Post;
import com.example.blogrestapi.exception.ResourceNotFoundException;
import com.example.blogrestapi.payload.PostDto;
import com.example.blogrestapi.repository.PostRepository;
import com.example.blogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Override
    public PostDto createPost(PostDto postDto) {

        //request state to entity
        Post post = dtoToEntity(postDto);
        postRepository.save(post);

        //entity to response state
        PostDto response = entityToDto(post);
        return response;
    }

    @Override
    public List<PostDto> getAllPost(int pageNo,int pageSize,
                                    String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //pagination and sorting
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Page<Post> posts = postRepository.findAll(pageable);

        //Content for page

        List<Post> postList = posts.getContent();
        return postList.stream()
                .map(post -> entityToDto(post))
                .collect(Collectors.toList());

    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException("Post","Id",id));
        PostDto postDto = entityToDto(post);
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {

        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException("Post","Id",id));

        existingPost.setTitle(postDto.getTitle() !=null ?
                postDto.getTitle() : existingPost.getTitle());

        existingPost.setDescription(postDto.getDescription() != null ?
                postDto.getDescription() : existingPost.getDescription());

        existingPost.setContent(postDto.getContent() != null ?
                postDto.getContent() : existingPost.getContent());

        postRepository.save(existingPost);
        PostDto postDto1 = entityToDto(existingPost);
        return postDto1;
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    private PostDto entityToDto(Post post){
        PostDto response = new PostDto();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setDescription(post.getDescription());
        response.setContent(post.getContent());
        return response;
    }
    private Post dtoToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
