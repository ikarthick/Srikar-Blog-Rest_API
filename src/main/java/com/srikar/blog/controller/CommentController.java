package com.srikar.blog.controller;

import com.srikar.blog.dto.CommentDTO;
import com.srikar.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(value = "postId") long postId,@Valid @RequestBody CommentDTO commentDTO){

        return new ResponseEntity<>(commentService.createComment(postId,commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(@PathVariable long postId){
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(value = "postId") long postId,@PathVariable(value = "id") long commentId){

        return new ResponseEntity<>(commentService.getCommentById(postId,commentId), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(value = "postId") long postId,@PathVariable(value = "id") long commentId,@Valid @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.updateComment(postId,commentId,commentDTO), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> deleteComment(@PathVariable(value = "postId") long postId,@PathVariable(value = "id") long commentId){
        return new ResponseEntity<>(commentService.deleteComment(postId,commentId), HttpStatus.OK);
    }




}
