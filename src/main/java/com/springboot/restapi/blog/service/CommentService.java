package com.springboot.restapi.blog.service;

import com.springboot.restapi.blog.model.Comment;
import com.springboot.restapi.blog.payload.ApiResponse;
import com.springboot.restapi.blog.payload.CommentRequest;
import com.springboot.restapi.blog.payload.PagedResponse;

public interface CommentService {

    PagedResponse<Comment> getAllComments(Long postId, int pageNum, int pageSize);

    public Comment addComment(CommentRequest commentRequest, Long postId);

    public Comment getCommentById(Long postId, Long id);

    public Comment updateComment(CommentRequest commentRequest, Long postId, Long id);

    public ApiResponse deleteComment(Long postId, Long id);

}
