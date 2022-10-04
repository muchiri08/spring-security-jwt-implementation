package com.muchiri.jwtimpl.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "User_Role",
			joinColumns = {
					@JoinColumn(name = "User_Id")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "Role_Id")
			}
			
						
		)
	private Set<Role> roles;

}
