package com.ifpr.gastronomique.api.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AulaDto {
	private Long id;
	private String descricao;
	private String nomePregao;
	private String nomeUsuario;
	private String nomeDisciplina;
	private String nomeLaboratorio;
	private LocalDate dataUtilizacao;
	private Double valor;
}
