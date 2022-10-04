package com.muchiri.jwtimpl.entity;

import lombok.Data;

@Data
public class JwtRequest {
	
	private String username;
	private String password;
	
}
