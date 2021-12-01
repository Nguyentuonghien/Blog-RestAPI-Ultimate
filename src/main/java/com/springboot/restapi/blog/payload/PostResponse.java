package com.springboot.restapi.blog.payload;

import lombok.Data;

@Data
public class PostResponse {

	private String title;
	private String body;

}
