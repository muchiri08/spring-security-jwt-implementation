package com.muchiri.jwtimpl.service;

import org.springframework.stereotype.Service;

import com.muchiri.jwtimpl.entity.Role;
import com.muchiri.jwtimpl.repository.RoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService {
	
	private final RoleRepository roleRepository;
	
	public Role createNewRole(Role role) {
		return roleRepository.save(role);
	}

}
