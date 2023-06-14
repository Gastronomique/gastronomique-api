package com.ifpr.gastronomique.api.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ListaDeCompra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate dataCriacao;
	
	@OneToMany(mappedBy = "listaDeCompra", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ItemListaDeCompra> itensDaListaDeCompra;
	
	public void addItensDaListaDeCompra(ItemListaDeCompra item) {		
		if(itensDaListaDeCompra == null) {
			itensDaListaDeCompra = new ArrayList<>();
		}
		itensDaListaDeCompra.add(item);
	}
	
	public boolean existeNaListaDeCompra(ItemListaDeCompra item) {
		for(ItemListaDeCompra itemListaDeCompra: itensDaListaDeCompra) {
			if(item.getInsumo().getId() == itemListaDeCompra.getInsumo().getId()) {
				return true;
			}
		}
		return false;
	}
}
