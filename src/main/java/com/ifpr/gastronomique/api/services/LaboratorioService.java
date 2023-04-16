package com.ifpr.gastronomique.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.api.models.Laboratorio;
import com.ifpr.gastronomique.api.repositories.LaboratorioRepository;

@Service
public class LaboratorioService {

	@Autowired
	private LaboratorioRepository repository;
	
	public List<Laboratorio> listarTodosLaboratorios() {
		return repository.findAll();
	}
	
	public ResponseEntity<Laboratorio> buscarLaboratorioPorId(Long id) {
		Laboratorio laboratorio = repository.findById(id).orElse(null);
		if(laboratorio != null) {
			return new ResponseEntity<Laboratorio>(laboratorio, HttpStatus.OK);
		}
		return new ResponseEntity<Laboratorio>(HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<Laboratorio> inserirLaboratorio(Laboratorio laboratorio) {
		repository.save(laboratorio);
		return new ResponseEntity<Laboratorio>(laboratorio, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Laboratorio> alterarLaboratorio(Long id, Laboratorio laboratorioAlterado) {
		Laboratorio laboratorio = repository.findById(laboratorioAlterado.getId()).orElse(null);
		if(laboratorio != null) {
			repository.save(laboratorioAlterado);
			return new ResponseEntity<Laboratorio>(laboratorio, HttpStatus.OK);
		}
		
		return new ResponseEntity<Laboratorio>(laboratorio, HttpStatus.NOT_FOUND);
		
	}
	
	public ResponseEntity<Laboratorio> excluirLaboratorioPorId(Long id) {
		Optional<Laboratorio> laboratorio = repository.findById(id);
		if(laboratorio.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}	
	}
}
