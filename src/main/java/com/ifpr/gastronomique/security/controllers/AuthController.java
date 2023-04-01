package com.ifpr.gastronomique.security.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifpr.gastronomique.security.jwt.JwtUtils;
import com.ifpr.gastronomique.security.payload.request.LoginRequest;
import com.ifpr.gastronomique.security.payload.request.SignupRequest;
import com.ifpr.gastronomique.security.payload.response.MessageResponse;
import com.ifpr.gastronomique.security.repositories.RoleRepository;
import com.ifpr.gastronomique.security.repositories.UserRepository;
import com.ifpr.gastronomique.security.services.UserService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return userService.loginUsuario(loginRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return userService.CadastrarNovoUsuario(signUpRequest);
	}
	
	@PostMapping("/signout")
	public ResponseEntity<?> signOutUser() {
	    return ResponseEntity.ok(new MessageResponse("User signed out successfully!"));
	}
}
