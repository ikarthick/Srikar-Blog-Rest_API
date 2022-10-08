package com.srikar.blog.service.impl;

import com.srikar.blog.dto.PostDTO;
import com.srikar.blog.dto.PostResponse;
import com.srikar.blog.entity.Post;
import com.srikar.blog.exception.ResourceNotFoundException;
import com.srikar.blog.repository.PostRepository;
import com.srikar.blog.service.CommentService;
import com.srikar.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {

        this.postRepository = postRepository;
        this.mapper  = mapper;
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
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy) {

        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Post> posts = postRepository.findAll(pageable);

        //get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDTO> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(long id) {
         Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
         return mapToDto(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        //get post by Id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public String deletePost(long id) {
         Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

         postRepository.delete(post);
         return "Post has been Deleted Successfully";

    }


    //Converted Entity to DTO
    private PostDTO mapToDto(Post post){

       PostDTO postDTO= mapper.map(post,PostDTO.class);

//        PostDTO postDTO = new PostDTO();
//        postDTO.setDescription(post.getDescription());
//        postDTO.setContent(post.getContent());
//        postDTO.setTitle(post.getTitle());
//        postDTO.setId(post.getId());
        return postDTO;
    }


    //Map DTO to Entity
    private Post mapToEntity(PostDTO postDTO){

        Post post = mapper.map(postDTO,Post.class);

//        Post post = new Post();
//        post.setId(postDTO.getId());
//        post.setDescription(postDTO.getDescription());
//        post.setContent(postDTO.getContent());
//        post.setTitle(postDTO.getTitle());
        return post;
    }


}
