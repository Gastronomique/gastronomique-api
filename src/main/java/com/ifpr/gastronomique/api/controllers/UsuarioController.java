package com.ifpr.gastronomique.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ifpr.gastronomique.domain.models.UsuarioModel;
import com.ifpr.gastronomique.domain.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping
	public List<UsuarioModel> findAll() {
		return service.listarTodosUsuarios();
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/{userId}")
	public ResponseEntity<UsuarioModel> findById(@PathVariable Long userId) {
		return service.listarUsuarioPorId(userId);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel create(@RequestBody UsuarioModel usuario) {
		return service.salvarUsuario(usuario);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/{userId}")
	public ResponseEntity<UsuarioModel> edit(@PathVariable Long userId, @RequestBody UsuarioModel usuario){
		return service.editarUsuario(userId, usuario);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> delete(@PathVariable Long userId) {
		return service.excluirUsuarioPorId(userId);
	}
}
