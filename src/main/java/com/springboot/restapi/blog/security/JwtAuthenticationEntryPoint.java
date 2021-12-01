package com.springboot.restapi.blog.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
	
	/**
	 * method commence() được gọi bất cứ khi nào một ngoại lệ được đưa ra do user chưa được xác thực đang cố gắng truy cập vào một tài nguyên yêu cầu xác thực
	 */	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			             AuthenticationException authException) throws IOException, ServletException {
		
		LOGGER.error("Responding with unauthorized error. Message - {}", authException.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sorry, You are not authorized to access this resource.");
	}
	
}
