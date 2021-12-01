package com.springboot.restapi.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.restapi.blog.model.role.Role;
import com.springboot.restapi.blog.model.role.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	public Optional<Role> findByRoleName(RoleName roleName);
	
}
