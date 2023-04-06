package com.ifpr.gastronomique.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.api.models.Insumo;
import com.ifpr.gastronomique.api.repositories.InsumoRepository;

@Service
public class InsumoService {

	@Autowired
	private InsumoRepository repository;
	
	public List<Insumo> listarTodosInsumos() {
		return repository.findAll();
	}
	
	public ResponseEntity<Insumo> buscarInsumoPorId(Long id) {
		Insumo insumo = repository.findById(id).orElse(null);
		if(insumo != null) {
			return new ResponseEntity<Insumo>(insumo, HttpStatus.OK);
		}
		return new ResponseEntity<Insumo>(HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<Insumo> inserirInsumo(Insumo insumo) {
		repository.save(insumo);
		return new ResponseEntity<Insumo>(insumo, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Insumo> alterarInsumo(Long id, Insumo insumoAlterado) {
		Insumo insumo = repository.findById(insumoAlterado.getId()).orElse(null);
		if(insumo != null) {
//			insumo.setDenominacao(insumoAlterado.getDenominacao());
//			insumo.setUnidadeDeMedida(insumoAlterado.getUnidadeDeMedida());
//			insumo.setDescricao(insumoAlterado.getDescricao());
//			insumo.setTipoInsumo(insumoAlterado.getTipoInsumo());
			repository.save(insumoAlterado);
			return new ResponseEntity<Insumo>(insumo, HttpStatus.OK);
		}
		
		return new ResponseEntity<Insumo>(insumo, HttpStatus.NOT_FOUND);
		
	}
	
	public ResponseEntity<Insumo> excluirInsumoPorId(Long id) {
		Optional<Insumo> insumo = repository.findById(id);
		if(insumo.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}	
	}
	
}