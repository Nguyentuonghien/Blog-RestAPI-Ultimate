package com.springboot.restapi.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException {

	private static final long serialVersionUID = 588449751424993189L;
	
	private final HttpStatus httpStatus;
    private final String message;

    public BlogApiException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public BlogApiException(HttpStatus httpStatus, String message, Throwable exception) {
        super(exception);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

}
