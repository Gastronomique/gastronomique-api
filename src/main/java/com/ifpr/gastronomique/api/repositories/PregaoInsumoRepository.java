package com.ifpr.gastronomique.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpr.gastronomique.api.models.Pregao;
import com.ifpr.gastronomique.api.models.PregaoInsumo;

public interface PregaoInsumoRepository extends JpaRepository<PregaoInsumo, Long> {
	List<PregaoInsumo> findByPregao(Pregao pregao);
}
