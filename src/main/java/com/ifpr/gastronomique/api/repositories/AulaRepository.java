package com.ifpr.gastronomique.api.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ifpr.gastronomique.api.models.Aula;
import com.ifpr.gastronomique.security.models.User;

public interface AulaRepository extends JpaRepository<Aula, Long> {
	
	List<Aula> findByUsuario(User usuario);
	
	@Query("SELECT a FROM aula a WHERE  a.dataUtilizacao BETWEEN :dataInicial AND :dataFinal")
	List<Aula> buscarPorPeriodo(LocalDate dataInicial, LocalDate dataFinal);
	
	@Query("SELECT a FROM aula a WHERE  a.listaDeCompra.id = :listaDeCompraId")
	List<Aula> buscarAulasPorListaDeCompraId(Long listaDeCompraId);
}