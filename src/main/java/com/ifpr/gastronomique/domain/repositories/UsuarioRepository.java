package com.ifpr.gastronomique.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpr.gastronomique.domain.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
