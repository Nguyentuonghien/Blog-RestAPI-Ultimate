package com.springboot.restapi.blog.service.impl;

import com.springboot.restapi.blog.exception.BlogApiException;
import com.springboot.restapi.blog.exception.ResourceNotFoundException;
import com.springboot.restapi.blog.model.Post;
import com.springboot.restapi.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.restapi.blog.model.Comment;
import com.springboot.restapi.blog.payload.ApiResponse;
import com.springboot.restapi.blog.payload.CommentRequest;
import com.springboot.restapi.blog.payload.PagedResponse;
import com.springboot.restapi.blog.repository.CommentRepository;
import com.springboot.restapi.blog.service.CommentService;
import com.springboot.restapi.blog.utils.AppUtils;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

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
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = new Comment();
        comment.setBody(commentRequest.getBody());
        comment.setEmail(commentRequest.getEmail());
        comment.setName(commentRequest.getName());
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    @Override
    public Comment getCommentById(Long postId, Long id) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        // check comment duoc tim thay co thuoc ve post khong?
        if (comment.getPost().getId().equals(post.getId())) {
            return comment;
        }
        throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post.");
    }

    @Override
    public Comment updateComment(CommentRequest commentRequest, Long postId, Long id) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post.");
        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        return commentRepository.save(comment);
    }

    @Override
    public ApiResponse deleteComment(Long postId, Long id) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        if (!comment.getPost().getId().equals(post.getId())) {
            new ApiResponse(Boolean.FALSE, "Comment does not belong to post.");
        }
        commentRepository.delete(comment);
        return new ApiResponse(Boolean.TRUE, "You successfully deleted comment.");
    }


}
