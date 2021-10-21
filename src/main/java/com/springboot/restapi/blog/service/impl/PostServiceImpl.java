package com.springboot.restapi.blog.service.impl;

import com.springboot.restapi.blog.exception.ResourceNotFoundException;
import com.springboot.restapi.blog.model.Post;
import com.springboot.restapi.blog.payload.ApiResponse;
import com.springboot.restapi.blog.payload.PagedResponse;
import com.springboot.restapi.blog.payload.PostRequest;
import com.springboot.restapi.blog.payload.PostResponse;
import com.springboot.restapi.blog.repository.PostRepository;
import com.springboot.restapi.blog.service.PostService;
import com.springboot.restapi.blog.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostResponse createPost(PostRequest postRequest) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setBody(postRequest.getBody());
        Post newPost = postRepository.save(post);

        PostResponse postResponse = new PostResponse();
        postResponse.setTitle(newPost.getTitle());
        postResponse.setBody(newPost.getBody());
        return postResponse;
    }

    @Override
    public Post updatePost(PostRequest postRequest, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setBody(postRequest.getBody());
        post.setTitle(postRequest.getTitle());
        Post updatedPost = postRepository.save(post);
        return updatedPost;
    }

    @Override
    public PagedResponse<Post> getAllPosts(int pageNum, int pageSize, String sortBy, String sortDir) {
        AppUtils.validatePageNumberAndSize(pageNum, pageSize);
        Sort sort = Sort.by(sortBy);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        // neu page khong co phan tu nao -> se tao 1 collection rong
        List<Post> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();
        PagedResponse pagedResponse = new PagedResponse();
        pagedResponse.setContent(content);
        pagedResponse.setPageNumber(posts.getNumber());
        pagedResponse.setPageSize(posts.getSize());
        pagedResponse.setTotalElements(posts.getTotalElements());
        pagedResponse.setTotalPages(posts.getTotalPages());
        pagedResponse.setLast(posts.isLast());

        return pagedResponse;
    }

    @Override
    public Post getPost(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return post;
    }

    @Override
    public ApiResponse deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
        return new ApiResponse(Boolean.TRUE, "You successfully deleted post.");
    }

}
