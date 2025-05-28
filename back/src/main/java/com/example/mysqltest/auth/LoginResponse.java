package com.example.mysqltest.auth;


import jakarta.annotation.Nonnull;


public class LoginResponse {
	
	@Nonnull
	private String token;
	@Nonnull
	private String refreshToken;
	
	public LoginResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
