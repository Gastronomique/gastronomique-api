package com.ifpr.gastronomique.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifpr.gastronomique.api.models.ListaDeCompra;
import com.ifpr.gastronomique.api.services.ListaDeCompraService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/lista/compra")
public class ListaDeCompraController {

	@Autowired
	private ListaDeCompraService service;
	
	@GetMapping
	public List<ListaDeCompra> listarTodasListasDeCompra() {
		return service.listarTodasListasDeCompra();
	}
	
	@PostMapping
	public ResponseEntity<ListaDeCompra> salvarListaDeCompra(@RequestBody List<Long> listaDeAulasId) {
		System.out.println("SALVOU UMA NOVA LISTA DE COMPRA...");
		return service.salvarListaDeCompra(listaDeAulasId);
	}
}
