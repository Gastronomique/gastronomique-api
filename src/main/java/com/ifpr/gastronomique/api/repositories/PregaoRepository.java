package com.ifpr.gastronomique.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.ifpr.gastronomique.api.models.Pregao;

@Repository
public interface PregaoRepository extends JpaRepository<Pregao, Long> {

}