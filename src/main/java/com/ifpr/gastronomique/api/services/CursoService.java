package com.ifpr.gastronomique.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.api.models.Curso;
import com.ifpr.gastronomique.api.repositories.CursoRepository;

@Service
public class CursoService {
	
	@Autowired
	private CursoRepository repository;
	
	public List<Curso> listarTodosCursos() {
		return repository.findAll();
	}
	
	public ResponseEntity<Curso> buscarCursoPorId(Long id) {
		Curso curso = repository.findById(id).orElse(null);
		if(curso != null) {
			return new ResponseEntity<Curso>(curso, HttpStatus.OK);
		}
		return new ResponseEntity<Curso>(HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<Curso> inserirCurso(Curso curso) {
		repository.save(curso);
		return new ResponseEntity<Curso>(curso, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Curso> alterarCurso(Long id, Curso cursoAlterado) {
		Curso curso = repository.findById(cursoAlterado.getId()).orElse(null);
		if(curso != null) {
			repository.save(cursoAlterado);
			return new ResponseEntity<Curso>(curso, HttpStatus.OK);
		}
		
		return new ResponseEntity<Curso>(curso, HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<Curso> excluirCursoPorId(Long id) {
		Optional<Curso> curso = repository.findById(id);
		if(curso.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}	
	}
}
