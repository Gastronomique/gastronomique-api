package com.ifpr.gastronomique.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.api.models.Disciplina;
import com.ifpr.gastronomique.api.repositories.DisciplinaRepository;

@Service
public class DisciplinaService {
	
	@Autowired
	private DisciplinaRepository repository;
	
	public List<Disciplina> listarTodasDisciplinas() {
		return repository.findAll();
	}
	
	public ResponseEntity<Disciplina> buscarDisciplinaPorId(Long id) {
		Disciplina disciplina = repository.findById(id).orElse(null);
		if(disciplina != null) {
			return new ResponseEntity<Disciplina>(disciplina, HttpStatus.OK);
		}
		return new ResponseEntity<Disciplina>(HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<Disciplina> inserirDisciplina(Disciplina disciplina) {
		repository.save(disciplina);
		return new ResponseEntity<Disciplina>(disciplina, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Disciplina> alterarDisciplina(Long id, Disciplina disciplinaAlterada) {
		Disciplina disciplina = repository.findById(disciplinaAlterada.getId()).orElse(null);
		if(disciplina != null) {
			repository.save(disciplinaAlterada);
			return new ResponseEntity<Disciplina>(disciplina, HttpStatus.OK);
		}
		
		return new ResponseEntity<Disciplina>(disciplina, HttpStatus.NOT_FOUND);
		
	}
	
	public ResponseEntity<Disciplina> excluirDisciplinaPorId(Long id) {
		Optional<Disciplina> disciplina = repository.findById(id);
		if(disciplina.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}	
	}
}
