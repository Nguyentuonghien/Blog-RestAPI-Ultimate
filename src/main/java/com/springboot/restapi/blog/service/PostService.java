package com.springboot.restapi.blog.service;

import com.springboot.restapi.blog.model.Post;
import com.springboot.restapi.blog.payload.ApiResponse;
import com.springboot.restapi.blog.payload.PagedResponse;
import com.springboot.restapi.blog.payload.PostRequest;
import com.springboot.restapi.blog.payload.PostResponse;

public interface PostService {

    public PostResponse createPost(PostRequest postRequest);

    PagedResponse<Post> getAllPosts(int pageNum, int pageSize, String sortBy, String sortDir);

    public Post updatePost(PostRequest postRequest, Long id);

    public Post getPost(long id);

    public ApiResponse deletePost(Long id);

}
