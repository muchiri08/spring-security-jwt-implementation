package com.muchiri.jwtimpl.controller;

import javax.annotation.PostConstruct;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.muchiri.jwtimpl.entity.User;
import com.muchiri.jwtimpl.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	//initial data
	@PostConstruct
	public void initUserAndRoles() {
		userService.initUserAndRoles();
	}
	
	@PostMapping("/registerNewUser")
	public User createNewUser(@RequestBody User user) {
		return userService.registerNewUser(user);
	}
	
	@GetMapping("/forAdmin")
	@PreAuthorize("hasRole('admin')")
	public String forAdmin() {
		return "This url is only accessible to the admins";
	}
	
	@GetMapping("/forUser")
	@PreAuthorize("hasRole('user')")
	public String forUser() {
		return "This url is only accessible to the users";
	}

}
