package com.ifpr.gastronomique.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.api.models.Aula;
import com.ifpr.gastronomique.api.models.ItemAula;
import com.ifpr.gastronomique.api.repositories.ItemAulaRepository;

@Service
public class ItemAulaService {
	@Autowired
	private ItemAulaRepository repository;
	
	public List<ItemAula> listarItensDaAula(Long idAula) {
		Aula aula = new Aula();
		aula.setId(idAula);
		return repository.findByAula(aula);
	}
	
	public ResponseEntity<ItemAula> buscarItemAulaPorId(Long itemAulaId) {
		ItemAula itemAula = repository.findById(itemAulaId).orElse(null);
		if(itemAula != null) {
			return new ResponseEntity<ItemAula>(itemAula, HttpStatus.OK);
		}
		return new ResponseEntity<ItemAula>(HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<ItemAula> inserirItemAula(ItemAula itemAula) {
		repository.save(itemAula);
		return new ResponseEntity<ItemAula>(itemAula, HttpStatus.CREATED);
	}
	
	public ResponseEntity<ItemAula> alterarItemAula(Long id, ItemAula itemAulaAlterado) {
		ItemAula itemAula = repository.findById(itemAulaAlterado.getId()).orElse(null);
		if(itemAula != null) {
			repository.save(itemAula);
			return new ResponseEntity<ItemAula>(itemAula, HttpStatus.OK);
		}
		
		return new ResponseEntity<ItemAula>(itemAula, HttpStatus.NOT_FOUND);
		
	}
	
	public ResponseEntity<ItemAula> excluirItemAulaPorId(Long id) {
		Optional<ItemAula> itemAula = repository.findById(id);
		if(itemAula.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}	
	}
}
