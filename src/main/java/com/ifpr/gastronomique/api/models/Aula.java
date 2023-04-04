package com.ifpr.gastronomique.api.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.ifpr.gastronomique.security.models.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Aula {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
	private User usuario;

	@ManyToOne
	@JoinColumn(name = "disciplina_id", referencedColumnName = "id", nullable = false)
	private Disciplina disciplina;
	
	@ManyToOne
	@JoinColumn(name = "laboratorio_id", referencedColumnName = "id", nullable = false)
	private Laboratorio laboratorio;
	
	private LocalDate dataUtilizacao;
	
	private Double valor;
}
