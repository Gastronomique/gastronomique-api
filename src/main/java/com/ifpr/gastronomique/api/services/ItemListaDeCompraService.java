package com.ifpr.gastronomique.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.api.models.ItemListaDeCompra;
import com.ifpr.gastronomique.api.models.ListaDeCompra;
import com.ifpr.gastronomique.api.repositories.ItemListaDeCompraRepository;
import com.ifpr.gastronomique.api.repositories.ListaDeCompraRepository;

@Service
public class ItemListaDeCompraService {

	@Autowired
	private ItemListaDeCompraRepository repository;
	
	@Autowired
	private ListaDeCompraRepository listaDeCompraRepository;
	
	public List<ItemListaDeCompra> listarItensDeCompraDaListaId(Long idListaDeCompra) {
		ListaDeCompra listaDeCompra = listaDeCompraRepository.findById(idListaDeCompra).orElse(null);
		if(listaDeCompra != null) {
			List<ItemListaDeCompra> listaDeItensDeCompra = repository.findByListaDeCompra(listaDeCompra);
			return listaDeItensDeCompra;
		}
		return null;
	}
	
	public ResponseEntity<ItemListaDeCompra> salvarItemListaDeCompra(ItemListaDeCompra item) {
		repository.save(item);
		return new ResponseEntity<ItemListaDeCompra>(item, HttpStatus.CREATED);
	}	
}