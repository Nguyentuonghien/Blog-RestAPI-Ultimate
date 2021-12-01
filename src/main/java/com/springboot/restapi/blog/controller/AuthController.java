package com.springboot.restapi.blog.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.restapi.blog.exception.AppException;
import com.springboot.restapi.blog.exception.BlogApiException;
import com.springboot.restapi.blog.model.role.Role;
import com.springboot.restapi.blog.model.role.RoleName;
import com.springboot.restapi.blog.model.user.User;
import com.springboot.restapi.blog.payload.ApiResponse;
import com.springboot.restapi.blog.payload.JwtAuthenticationResponse;
import com.springboot.restapi.blog.payload.LoginRequest;
import com.springboot.restapi.blog.payload.SignUpRequest;
import com.springboot.restapi.blog.repository.RoleRepository;
import com.springboot.restapi.blog.repository.UserRepository;
import com.springboot.restapi.blog.security.JwtTokenProvider;


/**
 *  API POST lấy username và password trong phần body, sử dụng Spring Authentication Manager, ta xác thực username và password, 
 *  nếu thông tin đăng nhập hợp lệ, mã thông báo JWT được tạo bằng JwtTokenProvider và được cung cấp cho client
 *
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				            new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username is already taken.");
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email is already taken.");
		}
		User user = new User();
		user.setFirstName(signUpRequest.getFirstName().toLowerCase());
		user.setLastName(signUpRequest.getLastName().toLowerCase());
		user.setUsername(signUpRequest.getUsername().toLowerCase());
		user.setEmail(signUpRequest.getEmail().toLowerCase());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		
		Set<Role> roles = new HashSet<>();
		if (userRepository.count() == 0) {
			roles.add(roleRepository.findByRoleName(RoleName.ROLE_USER)
			                  .orElseThrow(() -> new AppException("User role not set.")));
			roles.add(roleRepository.findByRoleName(RoleName.ROLE_ADMIN)
	                  .orElseThrow(() -> new AppException("User role not set.")));		
		} else {
			roles.add(roleRepository.findByRoleName(RoleName.ROLE_USER)
	                  .orElseThrow(() -> new AppException("User role not set.")));
		}
		user.setRoles(roles);
		userRepository.save(user);
		return new ResponseEntity<ApiResponse>(new ApiResponse(Boolean.TRUE, "User registered successfully."), HttpStatus.OK);
	}
	
	
}





