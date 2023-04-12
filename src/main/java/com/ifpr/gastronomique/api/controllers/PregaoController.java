package com.ifpr.gastronomique.api.controllers;

import java.util.List;

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

import com.ifpr.gastronomique.api.models.Pregao;
import com.ifpr.gastronomique.api.services.PregaoService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/pregoes")
public class PregaoController {
	
	@Autowired
	private PregaoService service;
	
	@GetMapping
	public List<Pregao> buscarTodos() {
		return service.listarTodosPregoes();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pregao> buscarPorId(@PathVariable Long id) {
		return service.buscarPregaoPorId(id);
	}
	
	@PostMapping
	public ResponseEntity<Pregao> inserirPregao(@RequestBody Pregao pregao) {
		return service.inserirPregao(pregao);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pregao> alterarPregao(@PathVariable Long id,@RequestBody Pregao pregao) {
		return service.alterarPregao(id, pregao);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Pregao> excluirPregaoPorId(@PathVariable Long id) {
		return service.excluirPregaoPorId(id);
	}
	
}