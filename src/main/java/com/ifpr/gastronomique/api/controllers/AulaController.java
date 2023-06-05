package com.ifpr.gastronomique.api.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.ifpr.gastronomique.api.dto.AulaDto;
import com.ifpr.gastronomique.api.models.Aula;
import com.ifpr.gastronomique.api.services.AulaService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/aulas")
public class AulaController {
	
	@Autowired
	private AulaService aulaService;
	
	@GetMapping
	public List<AulaDto> listarTodasAulas() {
		return aulaService.listarTodasAulas();
	}
	
	@GetMapping("/usuario/{idUsuario}")
	public List<AulaDto> listarAulasPorUsuario(@PathVariable Long idUsuario) {
		return aulaService.listarAulasPorUsuario(idUsuario);
	}
	
	@GetMapping("{dataInicial}/{dataFinal}")
	public List<Aula> listarAulasPorPeriodo(
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate dataInicial,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFinal
			) {
		return aulaService.listarAulasPorPeriodo(dataInicial, dataFinal);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Aula> buscarAulaPorId(@PathVariable Long id) {
		return aulaService.buscarAulaPorId(id);
	}
	
	@PostMapping
	public ResponseEntity<AulaDto> inserirAula(@Valid @RequestBody Aula aula) {
		return aulaService.inserirAula(aula);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Aula> alterarAula(@PathVariable Long id, @Valid @RequestBody Aula aula) {
		aula.setId(id);
		return aulaService.alterarAula(id, aula);
	}
	
	@PutMapping("/enviar/{id}")
	public ResponseEntity<Aula> enviarAula(@PathVariable Long id) {
		return aulaService.enviarAula(id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Aula> excluirAulaPorId(@PathVariable Long id) {
		return aulaService.excluirAulaPorId(id);
	}
}
