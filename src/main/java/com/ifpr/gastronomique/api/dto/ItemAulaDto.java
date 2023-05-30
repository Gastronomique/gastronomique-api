package com.ifpr.gastronomique.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemAulaDto {
	private Long id;
	private Long idAula;
	private Long idInsumo;
	private Double quantidade;
	private String observacao;
	private Double valorUnitario;
}
