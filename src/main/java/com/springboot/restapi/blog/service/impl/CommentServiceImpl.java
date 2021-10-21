package com.springboot.restapi.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.restapi.blog.model.Comment;
import com.springboot.restapi.blog.payload.ApiResponse;
import com.springboot.restapi.blog.payload.CommentRequest;
import com.springboot.restapi.blog.payload.PagedResponse;
import com.springboot.restapi.blog.repository.CommentRepository;
import com.springboot.restapi.blog.service.CommentService;
import com.springboot.restapi.blog.utils.AppUtils;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    
    @Override
    public PagedResponse<Comment> getAllComments(Long postId, int pageNum, int pageSize) {
        AppUtils.validatePageNumberAndSize(pageNum, pageSize);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Comment> page = commentRepository.findByPostId(postId, pageable);
        PagedResponse pagedResponse = new PagedResponse();
        pagedResponse.setContent(page.getContent());
        pagedResponse.setTotalPages(page.getTotalPages());
        pagedResponse.setTotalElements(page.getTotalElements());
        pagedResponse.setPageNumber(page.getNumber());
        pagedResponse.setPageSize(page.getSize());
        pagedResponse.setLast(page.isLast());
        return pagedResponse;
    }

    @Override
    public Comment addComment(CommentRequest commentRequest, Long postId) {
        return null;
    }

    @Override
    public Comment getCommentById(Long postId, Long id) {
        return null;
    }

    @Override
    public Comment updateComment(CommentRequest commentRequest, Long postId, Long id) {
        return null;
    }

    @Override
    public ApiResponse deleteComment(Long postId, Long id) {
        return null;
    }


}
