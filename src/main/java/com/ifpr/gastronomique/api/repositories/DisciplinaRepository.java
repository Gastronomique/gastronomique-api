package com.ifpr.gastronomique.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpr.gastronomique.api.models.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
	List<Disciplina> findByCursoId(Long cursoId);
}
