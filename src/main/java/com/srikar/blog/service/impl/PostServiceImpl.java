package com.srikar.blog.service.impl;

import com.srikar.blog.dto.PostDTO;
import com.srikar.blog.entity.Post;
import com.srikar.blog.repository.PostRepository;
import com.srikar.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {

        //convert DTO to entity
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        post.setId(postDTO.getId());

        Post newPost = postRepository.save(post);

        //convert entity to DTO
        PostDTO postResponse = new PostDTO();
        postResponse.setId(newPost.getId());
        postResponse.setContent(newPost.getContent());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setDescription(newPost.getDescription());

        return postResponse;
    }
}
