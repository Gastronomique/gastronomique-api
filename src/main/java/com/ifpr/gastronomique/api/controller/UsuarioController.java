package com.ifpr.gastronomique.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ifpr.gastronomique.domain.models.UsuarioModel;
import com.ifpr.gastronomique.domain.repositories.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository repository;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping
	public List<UsuarioModel> findAll() {
		return repository.findAll();
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/{idUser}")
	public ResponseEntity<UsuarioModel> findById(@PathVariable Long idUser) {
		Optional<UsuarioModel> usuario = repository.findById(idUser);
		return usuario.map(user -> ResponseEntity.ok(user))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel create(@RequestBody UsuarioModel usuario) {
		return repository.save(usuario);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{idUser}")
	public ResponseEntity<Void> delete(@PathVariable Long idUser) {
		if(!repository.existsById(idUser)) {
			return ResponseEntity.notFound().build();
		}
		repository.deleteById(idUser);
		return ResponseEntity.noContent().build();
	}
}
