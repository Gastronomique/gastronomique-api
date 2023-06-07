package com.ifpr.gastronomique.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.api.models.ListaDeCompra;
import com.ifpr.gastronomique.api.repositories.ListaDeCompraRepository;

@Service
public class ListaDeCompraService {

	@Autowired
	private ListaDeCompraRepository repository;
	
	
	public List<ListaDeCompra> listarTodasListasDeCompra() {
		return repository.findAll();
	}
	
	public ResponseEntity<ListaDeCompra> salvarListaDeCompra(ListaDeCompra lista) {
		repository.save(lista);
		return new ResponseEntity<ListaDeCompra>(lista, HttpStatus.CREATED);
	}
	
}
