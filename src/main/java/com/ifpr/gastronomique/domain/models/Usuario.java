package com.ifpr.gastronomique.domain.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ifpr.gastronomique.domain.enums.TipoUsuarioEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
public class Usuario {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private TipoUsuarioEnum tipoUsuario;
	
	@NotBlank
	private String nomeCompleto;
	
	@NotBlank
	@Size(max = 14)
	private String cpf;
	
	private String senha;
	
	@Email
	@NotBlank
	private String email;
	
	private boolean situacao;	
}