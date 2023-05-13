package com.ifpr.gastronomique.api.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ItemAula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "aula_id", referencedColumnName = "id", nullable = false)
	@JsonBackReference
	private Aula aula;
	
	@ManyToOne
	@JoinColumn(name = "insumo_id", referencedColumnName = "id", nullable = false)
	private Insumo insumo;
	
	private Double quantidade;
	
	private String observacao;
	
	private Double valorUnitario;
	
	private Double valorTotal;
}
