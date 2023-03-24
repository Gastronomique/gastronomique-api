package com.ifpr.gastronomique.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.domain.enums.RoleName;
import com.ifpr.gastronomique.domain.enums.TipoUsuarioEnum;
import com.ifpr.gastronomique.domain.models.RoleModel;
import com.ifpr.gastronomique.domain.models.UsuarioModel;
import com.ifpr.gastronomique.domain.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Transactional
	public List<UsuarioModel> listarTodosUsuarios() {
		return repository.findAll();
	}
	
	@Transactional
	public ResponseEntity<UsuarioModel> listarUsuarioPorId(Long userId) {
		Optional<UsuarioModel> usuario = repository.findById(userId);
		return usuario
				.map(user -> ResponseEntity.ok(user))
				.orElse(ResponseEntity.notFound().build());
	}

	@Transactional
	public UsuarioModel salvarUsuario(UsuarioModel usuario) {
		//Criptografar a senha antes de salvar no banco
		var senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getPassword());
		
		//Setar o E-mail como 'username'
		usuario.setUsername(usuario.getEmail());
		
		//Setar a senha criptografada do usuário
		usuario.setPassword(senhaCriptografada);
		
		var role = new RoleModel();
		if(usuario.getTipoUsuario().equals(TipoUsuarioEnum.PROFESSOR)) {
			role.setRoleId(2L);
			role.setRoleName(RoleName.ROLE_PROFESSOR);
		} else if(usuario.getTipoUsuario().equals(TipoUsuarioEnum.ADMINISTRADOR)) {
			role.setRoleId(1L);
			role.setRoleName(RoleName.ROLE_ADMIN);
		}
		
		List<RoleModel> roles = new ArrayList<>();
		roles.add(role);
		usuario.setRoles(roles);
		
		return repository.save(usuario);
	}
	
	@Transactional
	public ResponseEntity<UsuarioModel> editarUsuario(Long userId, UsuarioModel usuario) {
		if(!repository.existsById(userId)) {
			return ResponseEntity.notFound().build();
		}
		usuario.setUserId(userId);
		usuario = salvarUsuario(usuario);
		return ResponseEntity.ok(usuario);
	}
	
	@Transactional
	public ResponseEntity<Void> excluirUsuarioPorId(Long userId) {
		if(!repository.existsById(userId)) {
			return ResponseEntity.notFound().build();
		}
		repository.deleteById(userId);
		return ResponseEntity.noContent().build();
	}
	
	
}