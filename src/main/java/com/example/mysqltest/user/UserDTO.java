package com.example.mysqltest.user;

import jakarta.annotation.Nonnull;

public class UserDTO {

	@Nonnull
	private String username;
	@Nonnull
	private String email;
	@Nonnull
	private String password;
	@Nonnull
	private String role  = "USER";
	private String token;
	
	

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

	 
