package com.muchiri.jwtimpl.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.muchiri.jwtimpl.entity.Role;
import com.muchiri.jwtimpl.entity.User;
import com.muchiri.jwtimpl.repository.RoleRepository;
import com.muchiri.jwtimpl.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	
	public User registerNewUser(User user) {
		Role role = roleRepository.findById("user").get();
		
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	//default users and role to be in database every time we run run the application
	public void initUserAndRoles() {
		Role adminRole = new Role();
		adminRole.setRoleName("admin");
		adminRole.setDescription("Application administartor");
		roleRepository.save(adminRole);
		
		Role userRole = new Role();
		userRole.setRoleName("user");
		userRole.setDescription("Default role given to new record");
		roleRepository.save(userRole);
		
		User admin = new User();
		admin.setUsername("admin@123");
		admin.setFirstName("admin");
		admin.setLastName("admin");
		admin.setPassword(passwordEncoder.encode("admin@pass"));
		Set<Role> adminRoles = new HashSet<Role>();
		adminRoles.add(adminRole);
		admin.setRoles(adminRoles);
		userRepository.save(admin);
		
		
	}

}
