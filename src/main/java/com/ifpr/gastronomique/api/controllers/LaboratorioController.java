package com.ifpr.gastronomique.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifpr.gastronomique.api.models.Laboratorio;
import com.ifpr.gastronomique.api.services.LaboratorioService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/laboratorios")
public class LaboratorioController {
	
	@Autowired
	private LaboratorioService service;
	
	@GetMapping
	public List<Laboratorio> buscarTodos() {
		return service.listarTodosLaboratorios();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Laboratorio> buscarPorId(@PathVariable Long id) {
		return service.buscarLaboratorioPorId(id);
	}
	
	@PostMapping
	public ResponseEntity<Laboratorio> inserirLaboratorio(@Valid @RequestBody Laboratorio laboratorio) {
		return service.inserirLaboratorio(laboratorio);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Laboratorio> alterarLaboratorio(@PathVariable Long id, @Valid @RequestBody Laboratorio laboratorio) {
		return service.alterarLaboratorio(id, laboratorio);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Laboratorio> excluirLaboratorioPorId(@PathVariable Long id) {
		return service.excluirLaboratorioPorId(id);
	}
}
