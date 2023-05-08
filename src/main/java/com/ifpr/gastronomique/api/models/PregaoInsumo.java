package com.ifpr.gastronomique.api.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PregaoInsumo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "pregao_id", referencedColumnName = "id", nullable = false)
	@JsonBackReference
	private Pregao pregao;

	@ManyToOne
	@JoinColumn(name = "insumo_id", referencedColumnName = "id", nullable = false)
	private Insumo insumo;
	
	@NotNull
	private Double preco;
	
	@NotNull
	private Double quantidade;
}
