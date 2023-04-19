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

import com.ifpr.gastronomique.api.DisciplinaDto;
import com.ifpr.gastronomique.api.models.Disciplina;
import com.ifpr.gastronomique.api.services.DisciplinaService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {
	
	@Autowired
	private DisciplinaService service;
	
	@GetMapping
	public List<Disciplina> buscarTodos() {
		return service.listarTodasDisciplinas();
	}
	
	@GetMapping("curso/{cursoId}")
	public List<DisciplinaDto> listarDisciplinasPorCursoId(@PathVariable Long cursoId) {
		return service.listarDisciplinasPorCursoId(cursoId);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Disciplina> buscarPorId(@PathVariable Long id) {
		return service.buscarDisciplinaPorId(id);
	}
	
	@PostMapping("/{cursoId}")
	public ResponseEntity<Disciplina> inserirDisciplina(@Valid @RequestBody Disciplina disciplina, @PathVariable Long cursoId) {
		return service.inserirDisciplina(disciplina.getNome(), cursoId);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Disciplina> alterarDisciplina(@PathVariable Long id, @Valid @RequestBody Disciplina disciplina) {
		return service.alterarDisciplina(id, disciplina);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Disciplina> excluirDisciplinaPorId(@PathVariable Long id) {
		return service.excluirDisciplinaPorId(id);
	}
}
