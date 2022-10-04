package com.muchiri.jwtimpl.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.muchiri.jwtimpl.entity.Role;
import com.muchiri.jwtimpl.service.RoleService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class RoleController {
	
	private final RoleService roleService;
	
	@PostMapping("/createNewRole")
	public Role createNewRole(@RequestBody Role role) {
		
		return roleService.createNewRole(role);
	}

}
