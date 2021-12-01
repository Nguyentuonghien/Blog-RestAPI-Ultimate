package com.springboot.restapi.blog.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PostRequest {

	@NotBlank
	@Size(min = 10)
    private String title;

	@NotBlank
	@Size(min = 30)
    private String body;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

    

}
