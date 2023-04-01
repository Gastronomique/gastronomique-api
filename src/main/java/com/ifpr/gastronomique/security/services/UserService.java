package com.ifpr.gastronomique.security.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.security.jwt.JwtUtils;
import com.ifpr.gastronomique.security.models.ERole;
import com.ifpr.gastronomique.security.models.Role;
import com.ifpr.gastronomique.security.models.User;
import com.ifpr.gastronomique.security.payload.request.LoginRequest;
import com.ifpr.gastronomique.security.payload.request.SignupRequest;
import com.ifpr.gastronomique.security.payload.response.JwtResponse;
import com.ifpr.gastronomique.security.payload.response.MessageResponse;
import com.ifpr.gastronomique.security.repositories.RoleRepository;
import com.ifpr.gastronomique.security.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;
	
	@Transactional
	public ResponseEntity<?> loginUsuario(LoginRequest loginRequest) {
		
		//Verificando se o usuário está ATIVO
		boolean estaAtivo = repository.findByUsername(loginRequest.getUsername()).map(usuario -> usuario.isActive()).orElse(null);
		if(!estaAtivo) {
			return ResponseEntity.badRequest().body(new MessageResponse("O seu usuário esta INATIVO, contate o ADMINISTRADOR!"));
		}
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}
	
	@SuppressWarnings("null")
	@Transactional
	public ResponseEntity<?> CadastrarNovoUsuario(SignupRequest signUpRequest) {
		if (repository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		if (repository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(
				signUpRequest.getFullName(),
				signUpRequest.isActive(),
				signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));
		
		// Setando o username com o email
		user.setUsername(user.getEmail());
		
		// Usuário começa desativado até o ACEITE do ADMINISTRADOR: 0 = INATIVO, 1 = ATIVO
		user.setActive(false);

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		if(strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_PROFESSOR)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			for(String role : strRoles) {
				switch (role) {
				case "admin":
					Role roleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Role is not found!"));
					roles.add(roleAdmin);
					break;
				case "professor":
					Role roleProfessor = roleRepository.findByName(ERole.ROLE_PROFESSOR)
					.orElseThrow(() -> new RuntimeException("Role is not found!"));
					roles.add(roleProfessor);
					break;
				}
			}
		}
		
		user.setRoles(roles);
		repository.save(user);

		return ResponseEntity.ok(new MessageResponse("Cadastro Realizado Com Sucesso, Solicite Ao Administrador Que Ative Seu Usuário!"));
	}

}
