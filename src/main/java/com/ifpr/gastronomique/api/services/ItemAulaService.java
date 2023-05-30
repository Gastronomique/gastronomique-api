package com.ifpr.gastronomique.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.api.models.Aula;
import com.ifpr.gastronomique.api.models.ItemAula;
import com.ifpr.gastronomique.api.repositories.AulaRepository;
import com.ifpr.gastronomique.api.repositories.ItemAulaRepository;

@Service
public class ItemAulaService {
	@Autowired
	private ItemAulaRepository repository;
	
	@Autowired
	private AulaRepository aulaRepository;
	
	@Autowired
	private AulaService aulaService;
	
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
		Double quantidade = itemAula.getQuantidade();
		Double valorUnitario = itemAula.getValorUnitario();
		Double valorTotal = valorUnitario * quantidade;
		itemAula.setValorTotal(valorTotal);
		
		Optional<Aula> aula = aulaRepository.findById(itemAula.getAula().getId());
		if(aula.isPresent()) {
			Double valorAula = aula.get().getValor() + itemAula.getValorTotal();
			aula.get().setValor(valorAula);
			aulaService.alterarAula(itemAula.getAula().getId(), aula.get());
		}
		
		repository.save(itemAula);
		return new ResponseEntity<ItemAula>(itemAula, HttpStatus.CREATED);
	}
	
	public ResponseEntity<ItemAula> alterarItemAula(Long id, ItemAula itemAulaAlterado) {
		ItemAula itemAula = repository.findById(id).orElse(null);
		
		if(itemAula != null) {
			itemAula.setQuantidade(itemAulaAlterado.getQuantidade());
			
			repository.save(itemAula);
			return new ResponseEntity<ItemAula>(itemAula, HttpStatus.OK);
		}
		
		return new ResponseEntity<ItemAula>(itemAula, HttpStatus.NOT_FOUND);
		
	}
	
	public ResponseEntity<ItemAula> excluirItemAulaPorId(Long id) {
		Optional<ItemAula> itemAula = repository.findById(id);
		if(itemAula.isPresent()) {
			Optional<Aula> aula = aulaRepository.findById(itemAula.get().getAula().getId());
			if(aula.isPresent()) {
				Double valorAula = aula.get().getValor() - itemAula.get().getValorTotal();
				aula.get().setValor(valorAula);
				aulaService.alterarAula(itemAula.get().getAula().getId(), aula.get());
			}
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}	
	}
	
//	public List<Insumo> listarItensPregao(Long idAula) {
//		Optional<Aula> aula =  aulaRepository.findById(idAula);
//		if(aula.isPresent()) {
//			//Optional<Pregao> pregao = pregaoRepository.findById(aula.get().getPregao().getId());
//			Pregao pregao = new Pregao();
//		}
//	}
}
