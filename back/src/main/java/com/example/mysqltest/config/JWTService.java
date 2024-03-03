package com.example.mysqltest.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.UUID;
import java.util.function.Function;
import java.util.logging.Logger;

import javax.crypto.SecretKey;

@Service
public class JWTService {

//	SecretKey key = Jwts.SIG.HS256.key().build();
	private static final String secretKey = "secret_secret_secret_secret_secret_hey";
	SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
	String secretString = Encoders.BASE64.encode(key.getEncoded());
	

	private static final Logger logger = Logger.getLogger(JWTService.class.getName());
	
	//@formatter:off
	private Claims extractAllClaims(String token) {
		SecretKey verificationKey = getKey();
		try {
			return Jwts.parser()
					.verifyWith(verificationKey)
					.build()
					.parseSignedClaims(token)
					.getPayload();
		} catch (ExpiredJwtException e) {
			  Claims claims = e.getClaims();
			  return claims;
		}
	}
	//@formatter:on
	
	

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	private SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretString);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	//@formatter:off
	public String generateToken(String username) {
		Date now = new Date();
		Date exp = new Date(System.currentTimeMillis() + (1000 * 10));

		String jwt = Jwts.builder()
				.id(UUID.randomUUID().toString())
				.issuer("bookApp")
				.subject(username)
				.issuedAt(now)
				.expiration(exp)
				.signWith(key)
				.compact();

		return jwt;
	}
	//@formatter:on

	public String generateRefreshToken(String username) {
		Date now = new Date();
		Date exp = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7));

		String refreshToken = Jwts.builder()
				.id(UUID.randomUUID().toString())
				.issuer("bookApp")
				.subject(username)
				.issuedAt(now)
				.expiration(exp)
				.signWith(key)
				.compact();

		return refreshToken;
	}

}
