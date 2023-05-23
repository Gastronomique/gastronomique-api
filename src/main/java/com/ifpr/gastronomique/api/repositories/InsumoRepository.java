package com.ifpr.gastronomique.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifpr.gastronomique.api.models.Insumo;
import java.util.List;
import com.ifpr.gastronomique.api.enums.TipoInsumoEnum;


@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {
	List<Insumo> findByTipoInsumo(TipoInsumoEnum tipoInsumo);
}
