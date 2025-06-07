package com.example.mysqltest.config;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JWTService jwtService;
	private final UserDetailsService userDetailsService;

	public JwtAuthenticationFilter(JWTService jwtService, UserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	private static final Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getServletPath().contains("auth/**")) {
			filterChain.doFilter(request, response);
			return;
		}
		final String header = request.getHeader("Authorization");
		final String token;
		if (header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		logger.info("got token");
		token = header.substring(7);
//		logger.info(token);
		final String username = jwtService.extractUsername(token);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		if (jwtService.isTokenValid(token, userDetails)) {
				logger.info("valid");
				Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, null);
				SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			filterChain.doFilter(request,response);
			return;
		}
//		String refreshToken = request.getHeader("Refresh-Token");
//		if (refreshToken != null && jwtService.isTokenValid(refreshToken, userDetails)) {
//			String newToken = jwtService.generateToken(username);
//			Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, null);
//			SecurityContextHolder.getContext().setAuthentication(authentication);
//			response.setHeader("Authorization", "Bearer " + newToken);
//		}
		filterChain.doFilter(request,response);
}}
