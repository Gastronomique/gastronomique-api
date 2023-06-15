package com.ifpr.gastronomique.api.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.api.enums.StatusAulaEnum;
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
		
		//Cria lista de Aulas
		List<Aula> listaDeAulas = new ArrayList<>();
		
		// Cria lista de itens das aulas vazia
		List<ItemAula> listaDeItensAula = new ArrayList<>();
	    
	    //Popula os itens da listaDeItensAula
	    for (Long idAula : listaDeAulasId) {
			Aula aula = aulaRepository.findById(idAula).orElse(null);
			listaDeAulas.add(aula);
			if(aula != null) {
				for(ItemAula item: aula.getItensAula()) {
					listaDeItensAula.add(item);
				}
			}
		}
	    
	    //Cria uma nova lista de compra
	    ListaDeCompra listaDeCompra = new ListaDeCompra();
	    listaDeCompra.setDataCriacao(LocalDate.now());

	    //Adiciona itens da aula a listaDeCompra
	    for(ItemAula item : listaDeItensAula) {
	    	ItemListaDeCompra itemListaDeCompra = 
	    			listaDeCompra.existeInsumoNaListaDeCompra(item.getInsumo().getId());
	    	
	    	if(itemListaDeCompra == null) { 
		    	itemListaDeCompra = new ItemListaDeCompra();
			    itemListaDeCompra.setInsumo(item.getInsumo());
			    itemListaDeCompra.setQuantidade(item.getQuantidade());
			    itemListaDeCompra.setListaDeCompra(listaDeCompra);
			    listaDeCompra.addItensDaListaDeCompra(itemListaDeCompra);
	    	} else {
	    		itemListaDeCompra.setQuantidade(itemListaDeCompra.getQuantidade() + item.getQuantidade());
	    }	}    
	    
	    // Persistir os dados da lista de compra
	    listaDeCompra = listaDeCompraRepository.save(listaDeCompra);
	    
	    //Atualizar as aulas informando em qual lista de compras foram utilizadas
	    for (Aula aula : listaDeAulas) {
	    	aula.setStatus(StatusAulaEnum.FINALIZADA);
			aula.setListaDeCompra(listaDeCompra);
			aulaRepository.save(aula);
		}
	        
//	    return new ResponseEntity<ListaDeCompra>(listaDeCompra, HttpStatus.OK);
	    return ResponseEntity.status(HttpStatus.OK).body(listaDeCompra);
	}
	
}
