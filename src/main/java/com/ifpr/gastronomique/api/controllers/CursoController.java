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

import com.ifpr.gastronomique.api.models.Curso;
import com.ifpr.gastronomique.api.services.CursoService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/cursos")
public class CursoController {
	
	@Autowired
	private CursoService service;
	
	@GetMapping
	public List<Curso> buscarTodos() {
		return service.listarTodosCursos();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Curso> buscarPorId(@PathVariable Long id) {
		return service.buscarCursoPorId(id);
	}
	
	@PostMapping
	public ResponseEntity<Curso> inserirCurso(@Valid @RequestBody Curso curso) {
		return service.inserirCurso(curso);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Curso> alterarCurso(@PathVariable Long id, @Valid @RequestBody Curso curso) {
		return service.alterarCurso(id, curso);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Curso> excluirCursoPorId(@PathVariable Long id) {
		return service.excluirCursoPorId(id);
	}
}
