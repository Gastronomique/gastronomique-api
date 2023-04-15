package com.ifpr.gastronomique.api.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.ifpr.gastronomique.api.enums.TipoInsumoEnum;
import com.ifpr.gastronomique.api.enums.UnidadeDeMedidaEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Insumo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O campo DENOMINAÇÃO é obrigatório!")
	private String denominacao;
	
	@NotNull(message = "O campo UNIDADE DE MEDIDA é obrigatório!")
	@Enumerated(EnumType.STRING)
	private UnidadeDeMedidaEnum unidadeDeMedida;
	
	//Perguntar ao professor referente a capacidade deste campo...
	private String descricao;
	
	@NotNull(message = "O campo TIPO INSUMO é obrigatório!")
	@Enumerated(EnumType.STRING)
	private TipoInsumoEnum tipoInsumo;
	
}
