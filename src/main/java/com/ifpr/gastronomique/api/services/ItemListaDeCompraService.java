package com.ifpr.gastronomique.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.api.models.ItemListaDeCompra;
import com.ifpr.gastronomique.api.repositories.ItemListaDeCompraRepository;

@Service
public class ItemListaDeCompraService {

	@Autowired
	private ItemListaDeCompraRepository repository;
	
	public ResponseEntity<ItemListaDeCompra> salvarItemListaDeCompra(ItemListaDeCompra item) {
		repository.save(item);
		return new ResponseEntity<ItemListaDeCompra>(item, HttpStatus.CREATED);
	}	
}