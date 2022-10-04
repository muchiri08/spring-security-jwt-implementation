package com.muchiri.jwtimpl.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.muchiri.jwtimpl.service.JwtService;
import com.muchiri.jwtimpl.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	JwtService jwtService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String header =  request.getHeader("Authorization");
		
		String jwtToken = null;	
		String username = null;
		//making sure the header starts with "Bearer "
		if (header != null && header.startsWith("Bearer ")) {
			//used .length() to find the length of the string with is 7(inclusive of the space
			//Can be inserted 7 directly but not good approach
			jwtToken = header.substring("Bearer ".length()); 
			
			try {
				username = jwtUtil.getUsernameFromToken(jwtToken);
				
			} catch (IllegalArgumentException e) {
				log.error("Unable to get jwt token");
			} catch (ExpiredJwtException e) {
				log.error("Jwt token is expired");
			}
		}  else {
			log.error("Jwt token does not start with 'Bearer '");
		}
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = jwtService.loadUserByUsername(username);
			
			if (jwtUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
