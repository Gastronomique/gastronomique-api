package com.ifpr.gastronomique.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpr.gastronomique.api.models.Insumo;
import com.ifpr.gastronomique.api.models.ItemListaDeCompra;
import com.ifpr.gastronomique.api.models.ListaDeCompra;

public interface ItemListaDeCompraRepository extends JpaRepository<ItemListaDeCompra, Long> {
	List<ItemListaDeCompra> findByInsumo(Insumo insumo);
	List<ItemListaDeCompra> findByListaDeCompra(ListaDeCompra listaDeCompra);
}
