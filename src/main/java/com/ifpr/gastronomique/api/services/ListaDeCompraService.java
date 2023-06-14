package com.ifpr.gastronomique.api.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.api.models.Aula;
import com.ifpr.gastronomique.api.models.ItemAula;
import com.ifpr.gastronomique.api.models.ItemListaDeCompra;
import com.ifpr.gastronomique.api.models.ListaDeCompra;
import com.ifpr.gastronomique.api.repositories.AulaRepository;
import com.ifpr.gastronomique.api.repositories.InsumoRepository;
import com.ifpr.gastronomique.api.repositories.ItemListaDeCompraRepository;
import com.ifpr.gastronomique.api.repositories.ListaDeCompraRepository;

@Service
public class ListaDeCompraService {

	@Autowired
	private ListaDeCompraRepository repository;
	
	@Autowired
	private AulaRepository aulaRepository;
	
	@Autowired
	private ListaDeCompraRepository listaDeCompraRepository;
	
	@Autowired
	ItemListaDeCompraRepository itemListaDeCompraRepository;
	
	@Autowired 
	InsumoRepository insumoRepository;
	
	public List<ListaDeCompra> listarTodasListasDeCompra() {
		return repository.findAll();
	}
	
	//IMPLEMENTAR O QUANTO ANTES(TA ACABANDO O TEMPO)
	public ResponseEntity<ListaDeCompra> salvarListaDeCompra(List<Long> listaDeAulasId) {
	    
		// Cria lista de itens das aulas vazia
		List<ItemAula> listaDeItensAula = new ArrayList<>();
	    
		//Cria uma nova lista de compra
		ListaDeCompra listaDeCompra = new ListaDeCompra();
	    listaDeCompra.setDataCriacao(LocalDate.now());
	    //listaDeCompraRepository.save(listaDeCompra);
	    
	    //Popula os itens da listaDeItensAula
	    for (Long idAula : listaDeAulasId) {
			Aula aula = aulaRepository.findById(idAula).orElse(null);
			if(aula != null) {
				for(ItemAula item: aula.getItensAula()) {
					listaDeItensAula.add(item);
				}
			}
		}
	    
	    //Adiciona itens da aula a listaDeCompra
	    ItemListaDeCompra itemListaDeCompra;
	    
	    for(ItemAula item : listaDeItensAula) {
	    	
	    	itemListaDeCompra = new ItemListaDeCompra();
		    itemListaDeCompra.setInsumo(item.getInsumo());
		    itemListaDeCompra.setQuantidade(item.getQuantidade());
		    itemListaDeCompra.setListaDeCompra(listaDeCompra);
		    
//		    if(listaDeCompra.existeNaListaDeCompra(itemListaDeCompra)) {
//		    	System.out.println("Existe...");
//		    }
		    
		    listaDeCompra.addItensDaListaDeCompra(itemListaDeCompra);
	    }	    
	    
	    listaDeCompraRepository.save(listaDeCompra);
	    
	    //System.out.println(listaDeItensAula.size());    
	    return new ResponseEntity<ListaDeCompra>(listaDeCompra, HttpStatus.OK);
	}
	
//	private boolean existeNaListaDeCompras(Long idInsumo) {
//		Insumo insumo = insumoRepository.findById(idInsumo).orElse(null);
//		List<ItemListaDeCompra> itemListaDeCompra = itemListaDeCompraRepository.findByInsumo(insumo);
//		if(itemListaDeCompra.isEmpty()) {
//			return false;
//		}
//		return true;
//	}
	
}
