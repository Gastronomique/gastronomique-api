package com.ifpr.gastronomique.api.services;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.api.enums.StatusAulaEnum;
import com.ifpr.gastronomique.api.models.Aula;
import com.ifpr.gastronomique.api.repositories.AulaRepository;
import com.ifpr.gastronomique.security.models.ERole;
import com.ifpr.gastronomique.security.models.Role;
import com.ifpr.gastronomique.security.models.User;
import com.ifpr.gastronomique.security.repositories.RoleRepository;
import com.ifpr.gastronomique.security.repositories.UserRepository;

@Service
public class AdminService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AulaRepository aulaRepository;
	
	public ResponseEntity<User> ativarUsuario(Long userId) {
		var usuario = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(userId, "User"));
		usuario.setActive(true);
		userRepository.save(usuario);
		return new ResponseEntity<User>(usuario, HttpStatus.OK);
	}
	
	public ResponseEntity<User> desativarUsuario(Long userId) {
		var usuario = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(userId, "User"));
		usuario.setActive(false);
		userRepository.save(usuario);
		return new ResponseEntity<User>(usuario, HttpStatus.OK);
	}
	
	public ResponseEntity<User> tornarUsuarioAdmin(Long userId) {
		var usuario = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(userId, "User"));
		
		Set<Role> roles = new HashSet<>();
		Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(null);
		Role professorRole = roleRepository.findByName(ERole.ROLE_PROFESSOR).orElseThrow(null);
		roles.add(adminRole);
		roles.add(professorRole);
		usuario.setRoles(roles);
		
		userRepository.save(usuario);
		return new ResponseEntity<User>(usuario, HttpStatus.OK);
	}
	
	public ResponseEntity<Aula> aprovarAula(Long id) {
		Aula aula = aulaRepository.findById(id).orElse(null);
		if(aula != null) {
			aula.setStatus(StatusAulaEnum.APROVADA);
			aulaRepository.save(aula);
			return new ResponseEntity<Aula>(aula, HttpStatus.OK);
		}	
		return new ResponseEntity<Aula>(aula, HttpStatus.NOT_FOUND);
 }

}
