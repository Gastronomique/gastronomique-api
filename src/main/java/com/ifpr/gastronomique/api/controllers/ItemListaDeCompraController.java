package com.ifpr.gastronomique.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifpr.gastronomique.api.models.ItemListaDeCompra;
import com.ifpr.gastronomique.api.services.ItemListaDeCompraService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/lista/compra/item")
public class ItemListaDeCompraController {
	
	@Autowired
	private ItemListaDeCompraService service;
	
	@GetMapping("/{idListaDeCompra}")
	public List<ItemListaDeCompra> listarItensDeCompraDaListaId(@PathVariable Long idListaDeCompra) {
		return service.listarItensDeCompraDaListaId(idListaDeCompra);
	}
	
	@PostMapping
	public ResponseEntity<ItemListaDeCompra> salvarItemListaDeCompra(@RequestBody ItemListaDeCompra item) {
		System.out.println("SALVOU UM NOVO ITEM NA LISTA: " + item.getListaDeCompra().getId());
		return service.salvarItemListaDeCompra(item);
	}
	
}
