package com.springboot.restapi.blog.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.springboot.restapi.blog.model.audit.UserDateAudit;
import com.springboot.restapi.blog.model.user.User;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "posts", uniqueConstraints = @UniqueConstraint(columnNames = "title"))
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Post extends UserDateAudit {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", nullable = false)
    private String body;
    
    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    
    @ManyToOne(fetch = FetchType.LAZY)    // khi lấy đtượng Post từ DB sẽ không lấy theo đtượng User
    @JoinColumn(name = "user_id")
    private User user;
    
    @JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Comment> getComments() {
		return comments == null ? null : new ArrayList<>(comments);
	}

	public void setComments(List<Comment> comments) {
		if (comments == null) {
			this.comments = null;
		} else {
			this.comments = Collections.unmodifiableList(comments);
		}
	}


}



