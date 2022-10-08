package com.srikar.blog.service;

import com.srikar.blog.dto.PostDTO;
import com.srikar.blog.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy);

    PostDTO getPostById(long id);

    PostDTO updatePost(PostDTO postDTO, long id);

    String deletePost(long id);


}
