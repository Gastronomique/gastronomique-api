package com.ifpr.gastronomique.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifpr.gastronomique.api.repositories.AdminRepository;
import com.ifpr.gastronomique.api.services.AdminService;
import com.ifpr.gastronomique.security.models.User;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@GetMapping("listar/usuarios")
	public List<User> listarUsuarios() {
		return adminRepository.findAll();
	}
	
	@PutMapping("ativar/usuario/{userId}")
	public ResponseEntity<User> ativarUsuario(@PathVariable Long userId) {
		return adminService.ativarUsuario(userId);
	}
	
	@PutMapping("desativar/usuario/{userId}")
	public ResponseEntity<User> desativarUsuario(@PathVariable Long userId) {
		return adminService.desativarUsuario(userId);
	}
	
	@PutMapping("tornar/administrador/{userId}")
	public ResponseEntity<User> tornarUsuarioAdmin(@PathVariable Long userId) {
		return adminService.tornarUsuarioAdmin(userId);
	}
}
