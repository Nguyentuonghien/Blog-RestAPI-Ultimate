package com.springboot.restapi.blog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignUpRequest {
	
	@NotBlank
	@Size(min = 4, max = 40)
	private String firstName;
	
	@NotBlank
	@Size(min = 4, max = 40)
	private String lastName;
	
	@NotBlank
	@Size(min = 3, max = 20)
	private String username;
	
	@NotBlank
	@Size(min = 5, max = 20)
	private String password;
	
	@Email
	@NotBlank
	@Size(max = 40)
	private String email;
	
}



