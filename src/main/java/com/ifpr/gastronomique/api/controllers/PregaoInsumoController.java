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

import com.ifpr.gastronomique.api.models.PregaoInsumo;
import com.ifpr.gastronomique.api.services.PregaoInsumoService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/pregao/insumos")
public class PregaoInsumoController {

	@Autowired
	private PregaoInsumoService pregaoInsumoService;
	
	@GetMapping("/{idPregao}")
	public List<PregaoInsumo> listarInsumosDoPregao(@PathVariable Long idPregao) {
		return pregaoInsumoService.listarInsumosDoPregao(idPregao);
	}
	
	@PostMapping
	public ResponseEntity<PregaoInsumo> inserirInsumoNoPregao(@Valid @RequestBody PregaoInsumo pregaoInsumo) {
		return pregaoInsumoService.inserirPregaoInsumo(pregaoInsumo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PregaoInsumo> alterarPregaoInsumo(@PathVariable Long id, @Valid @RequestBody PregaoInsumo pregaoInsumo) {
		pregaoInsumo.setId(id);
		return pregaoInsumoService.alterarPregaoInsumo(id, pregaoInsumo);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PregaoInsumo> excluirPregaoInsumoPorId(@PathVariable Long id) {
		return pregaoInsumoService.excluirPregaoInsumoPorId(id);
	}
}
