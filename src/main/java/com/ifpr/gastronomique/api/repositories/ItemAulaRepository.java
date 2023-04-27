package com.ifpr.gastronomique.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpr.gastronomique.api.models.Aula;
import com.ifpr.gastronomique.api.models.ItemAula;

public interface ItemAulaRepository extends JpaRepository<ItemAula, Long> {
	
	List<ItemAula> findByAula(Aula aula);

}
