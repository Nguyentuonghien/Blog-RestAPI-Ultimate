package com.springboot.restapi.blog.controller;

import com.springboot.restapi.blog.model.Comment;
import com.springboot.restapi.blog.payload.ApiResponse;
import com.springboot.restapi.blog.payload.CommentRequest;
import com.springboot.restapi.blog.payload.PagedResponse;
import com.springboot.restapi.blog.service.CommentService;
import com.springboot.restapi.blog.utils.AppConstants;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Comment> addComment(@Valid @RequestBody CommentRequest commentRequest,
                                              @PathVariable("postId") Long postId) {
        Comment comment = commentService.addComment(commentRequest, postId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<PagedResponse<Comment>> getAllComments(
               @PathVariable("postId") Long postId,
               @RequestParam(name = "pageNum", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNum,
               @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize) {
        PagedResponse<Comment> pagedResponse = commentService.getAllComments(postId, pageNum, pageSize);
        return new ResponseEntity<>(pagedResponse, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable("postId") Long postId,
                                              @PathVariable("id") Long id) {
        Comment comment = commentService.getCommentById(postId, id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Comment> updateComment(@Valid @RequestBody CommentRequest commentRequest,
                                                 @PathVariable("postId") Long postId,
                                                 @PathVariable("id") Long id) {
        Comment comment = commentService.updateComment(commentRequest, postId, id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("postId") Long postId,
                                                     @PathVariable("id") Long id) {
        ApiResponse apiResponse = commentService.deleteComment(postId, id);
        HttpStatus httpStatus = apiResponse.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(apiResponse, httpStatus);
    }

}



