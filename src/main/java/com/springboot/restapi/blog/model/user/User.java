package com.springboot.restapi.blog.model.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.restapi.blog.model.Comment;
import com.springboot.restapi.blog.model.Post;
import com.springboot.restapi.blog.model.audit.DateAudit;
import com.springboot.restapi.blog.model.role.Role;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper=true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username"), 
                                            @UniqueConstraint(columnNames = "email")}
)
public class User extends DateAudit {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name")
	@NotBlank
	@Size(max = 40)
	private String firstName;
	
	@Column(name = "last_name")
	@NotBlank
	@Size(max = 40)
	private String lastName;
	
	@Column(name = "username")
	@NotBlank
	@Size(max = 15)
	private String username;
	
	@Email
	@NotBlank
	@Size(max = 40)
	@NaturalId
	private String email;
	
	@NotBlank
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // field này sẽ được Serialize sang chuỗi JSON
	@Size(max = 100)
	private String password;
	
	private String phone;
	
	private String website;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_role",
	           joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
	           inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id")
    )
	private Set<Role> roles;
	
	@JsonIgnore   // loại trừ posts trong quá trình Serialization and Deserialization dữ liệu JSON
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Post> posts;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments;
	
	public User(String firstName, String lastName, String username, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles == null ? null : new HashSet<>();
	}

	public void setRoles(Set<Role> roles) {
		if (roles == null) {
			this.roles = null;
		} else {
			this.roles = Collections.unmodifiableSet(roles);
		}
	}

	public List<Post> getPosts() {
		return posts == null ? null : new ArrayList<>();
	}

	public void setPosts(List<Post> posts) {
		if (posts == null) {
			this.posts = null;
		} else {
			this.posts = Collections.unmodifiableList(posts);
		}
	}

	public List<Comment> getComments() {
		return comments == null ? null : new ArrayList<>();
	}

	public void setComments(List<Comment> comments) {
		if (comments == null) {
			this.comments = null;
		} else {
			this.comments = Collections.unmodifiableList(comments);
		}
	}
	
	
	
}



