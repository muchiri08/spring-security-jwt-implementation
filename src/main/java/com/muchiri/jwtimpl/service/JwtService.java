package com.muchiri.jwtimpl.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.muchiri.jwtimpl.entity.JwtRequest;
import com.muchiri.jwtimpl.entity.JwtResponse;
import com.muchiri.jwtimpl.entity.User;
import com.muchiri.jwtimpl.repository.UserRepository;
import com.muchiri.jwtimpl.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
		String username = jwtRequest.getUsername();
		String password = jwtRequest.getPassword();
		
		authenticate(username, password);
		
		final UserDetails userDetails = loadUserByUsername(username);
		
		String newGeneratedToken = jwtUtil.generateToken(userDetails);
		
		User user = userRepository.findById(username).get();
		
		return new JwtResponse(user, newGeneratedToken);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findById(username).get();
		
		if (user !=null) {
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
		}  else {
			throw new UsernameNotFoundException("Username not valid");
		}
		
	}
	
	private Set getAuthorities(User user) {
		Set authorities = new HashSet<>();
		
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
		});
		
		return authorities;
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("User disabled");
		} catch (BadCredentialsException e) {
			throw new Exception("Bad credetials from user");
		}
	}

}
