package com.srikar.blog.service;

import com.srikar.blog.dto.CommentDTO;
import com.srikar.blog.entity.Comment;

import java.util.List;

public interface CommentService {

    CommentDTO createComment(long postId, CommentDTO commentDTO);

    List<CommentDTO> getCommentsByPostId(long postId);

    CommentDTO getCommentById(Long postId, Long commentId);

    CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO);

    CommentDTO deleteComment(Long postId, Long commentId);

}
