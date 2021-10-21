package com.springboot.restapi.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.restapi.blog.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // @Query("SELECT c FROM Comment c WHERE c.post.id = ?1")
    Page<Comment> findByPostId(Long postId, Pageable pageable);

}
