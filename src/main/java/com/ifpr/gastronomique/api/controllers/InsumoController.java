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

import com.ifpr.gastronomique.api.models.Insumo;
import com.ifpr.gastronomique.api.services.InsumoService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/insumos")
public class InsumoController {
	
	@Autowired
	private InsumoService service;
	
	@GetMapping
	public List<Insumo> buscarTodos() {
		return service.listarTodosInsumos();
	}
	
	@GetMapping("pregao/{idPregao}")
	public List<Insumo> buscarInsumosPorPregao(@PathVariable Long idPregao) {
		return service.buscarInsumosPorPregao(idPregao);
	}
	
	@GetMapping("tipo/{tipo}")
	public List<Insumo> buscarPorTipo(@PathVariable String tipo) {
		return service.buscarInsumosPorTipo(tipo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Insumo> buscarPorId(@PathVariable Long id) {
		return service.buscarInsumoPorId(id);
	}
	
	@PostMapping
	public ResponseEntity<Insumo> inserirInsumo(@Valid @RequestBody Insumo insumo) {
		return service.inserirInsumo(insumo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Insumo> alterarInsumo(@PathVariable Long id, @Valid @RequestBody Insumo insumo) {
		return service.alterarInsumo(id, insumo);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Insumo> excluirInsumoPorId(@PathVariable Long id) {
		return service.excluirInsumoPorId(id);
	}
	
}
