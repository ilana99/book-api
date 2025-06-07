package com.example.mysqltest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.mysqltest.config.JWTService;
import com.example.mysqltest.user.CustomUserDetailsService;
import com.example.mysqltest.user.User;
import com.example.mysqltest.user.UserDTO;
import com.example.mysqltest.user.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;


@RestController
@RequestMapping(path = "/auth")
public class AuthController {

	@Autowired
	private final CustomUserDetailsService userService;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;
	

	AuthController(UserRepository userRepository, CustomUserDetailsService userService, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, JWTService jwtService) {
		this.userRepository = userRepository;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	@PostMapping(path = "/register")
	public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
		try {
			User newUser = new User();
			newUser.setUsername(userDTO.getUsername());
			newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			newUser.setEmail(userDTO.getEmail());
			newUser.setRole(userDTO.getRole());
			userRepository.save(newUser);
			return new ResponseEntity<>("User added", HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("User not added", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/login")
	public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
		try {

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			

			String token = jwtService.generateToken(userDetails.getUsername());
			String refreshToken = jwtService.generateRefreshToken(userDetails.getUsername());
			
			LoginResponse loginResponse = new LoginResponse(token, refreshToken);

			return ResponseEntity.ok(loginResponse);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("error");
		}
	}

//	@GetMapping(path="/refresh")
//	public ResponseEntity<?> refreshToken(){
//		return "e";
//	}
	
	@GetMapping(path = "/")
	public String home() {
		System.out.println("test");
		return "test";
	}
	
//	@GetMapping(path = "/getAuthenticated")
//	public String getAuthenticated() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		String currentPrincipalName = authentication.getName();
//		return currentPrincipalName;
//	}

	@GetMapping(path = "/logout")
	public String lougoutUser() {
		return "logout";
	}

}
