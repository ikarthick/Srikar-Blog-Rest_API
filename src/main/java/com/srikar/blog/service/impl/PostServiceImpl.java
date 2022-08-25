package com.srikar.blog.service.impl;

import com.srikar.blog.dto.PostDTO;
import com.srikar.blog.entity.Post;
import com.srikar.blog.repository.PostRepository;
import com.srikar.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        Post post = mapToEntity(postDTO);

        Post newPost = postRepository.save(post);

        //convert entity to DTO
        PostDTO postResponse = mapToDto(newPost);

        return postResponse;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }


    //Converted Entity to DTO
    private PostDTO mapToDto(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());
        postDTO.setTitle(post.getTitle());
        postDTO.setId(post.getId());
        return postDTO;
    }


    //Map DTO to Entity
    private Post mapToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        post.setTitle(postDTO.getTitle());
        return post;
    }


}
