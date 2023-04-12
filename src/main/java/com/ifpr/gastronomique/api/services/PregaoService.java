package com.ifpr.gastronomique.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.api.models.Pregao;
import com.ifpr.gastronomique.api.repositories.PregaoRepository;

@Service
public class PregaoService {

	@Autowired
	private PregaoRepository repository;
	
	public List<Pregao> listarTodosPregoes() {
		return repository.findAll();
	}
	
	public ResponseEntity<Pregao> buscarPregaoPorId(Long id) {
		Pregao pregao = repository.findById(id).orElse(null);
		if(pregao != null) {
			return new ResponseEntity<Pregao>(pregao, HttpStatus.OK);
		}
		return new ResponseEntity<Pregao>(HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<Pregao> inserirPregao(Pregao pregao) {
		repository.save(pregao);
		return new ResponseEntity<Pregao>(pregao, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Pregao> alterarPregao(Long id, Pregao pregaoAlterado) {
		Pregao pregao = repository.findById(pregaoAlterado.getId()).orElse(null);
		if(pregao != null) {
			repository.save(pregaoAlterado);
			return new ResponseEntity<Pregao>(pregao, HttpStatus.OK);
		}
		
		return new ResponseEntity<Pregao>(pregao, HttpStatus.NOT_FOUND);
		
	}
	
	public ResponseEntity<Pregao> excluirPregaoPorId(Long id) {
		Optional<Pregao> pregao = repository.findById(id);
		if(pregao.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}	
	}
	
}