package com.ifpr.gastronomique.api.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.api.DisciplinaDto;
import com.ifpr.gastronomique.api.models.Curso;
import com.ifpr.gastronomique.api.models.Disciplina;
import com.ifpr.gastronomique.api.repositories.CursoRepository;
import com.ifpr.gastronomique.api.repositories.DisciplinaRepository;

@Service
public class DisciplinaService {
	
	@Autowired
	private DisciplinaRepository repository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	public List<Disciplina> listarTodasDisciplinas() {
		return repository.findAll();
	}
	
	public List<DisciplinaDto> listarDisciplinasPorCursoId(Long cursoId) {
		List<Disciplina> disciplinas = repository.findByCursoId(cursoId);
		return disciplinas.stream().map(d -> {
			DisciplinaDto dto = new DisciplinaDto();
			dto.setId(d.getId());
			dto.setNome(d.getNome());
			return dto;
		}).collect(Collectors.toList());
	}
	
	public ResponseEntity<Disciplina> buscarDisciplinaPorId(Long id) {
		Disciplina disciplina = repository.findById(id).orElse(null);
		if(disciplina != null) {
			return new ResponseEntity<Disciplina>(disciplina, HttpStatus.OK);
		}
		return new ResponseEntity<Disciplina>(HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<Disciplina> inserirDisciplina(String nomeDisciplina, Long cursoId) {
		Optional<Curso> curso = cursoRepository.findById(cursoId);
		
		if(!curso.isPresent()) {
			throw new ObjectNotFoundException(cursoId, "Curso");
		}
		
		Disciplina disciplina = new Disciplina();
		
		disciplina.setNome(nomeDisciplina);
		disciplina.setCurso(curso.get());
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
