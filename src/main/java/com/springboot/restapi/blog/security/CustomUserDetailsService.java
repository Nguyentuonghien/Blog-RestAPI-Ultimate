package com.springboot.restapi.blog.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.restapi.blog.model.role.Role;
import com.springboot.restapi.blog.model.user.User;
import com.springboot.restapi.blog.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
		                  .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));	
		
		// trả về một implementation của UserDetails, đối tượng này sẽ chứa đựng các thông tin cần thiết về user(username, password và các quyền) 
		// từ đó giúp Spring Security quản lý được phân quyền mà ta đã cấu hình trong class SecurityConfig
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());
	}
	
}



