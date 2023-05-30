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

import com.ifpr.gastronomique.api.models.ItemAula;
import com.ifpr.gastronomique.api.services.ItemAulaService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/aula/itens")
public class ItemAulaController {
	
	@Autowired
	private ItemAulaService itemAulaService;
	
	@GetMapping("/buscar/{itemAulaId}")
	public ResponseEntity<ItemAula> buscarItemAulaPorId(@PathVariable Long itemAulaId) {
		return itemAulaService.buscarItemAulaPorId(itemAulaId);
	}
	
	@GetMapping("/{idAula}")
	public List<ItemAula> listarItensDaAula(@PathVariable Long idAula) {
		return itemAulaService.listarItensDaAula(idAula);
	}
	
	@PostMapping
	public ResponseEntity<ItemAula> inserirItemAula(@Valid @RequestBody ItemAula itemAula) {
		return itemAulaService.inserirItemAula(itemAula);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ItemAula> alterarItemAula(@PathVariable Long id, @Valid @RequestBody ItemAula itemAula) {
		return itemAulaService.alterarItemAula(id, itemAula);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ItemAula> excluirItemAulaPorId(@PathVariable Long id) {
		return itemAulaService.excluirItemAulaPorId(id);
	}

}
