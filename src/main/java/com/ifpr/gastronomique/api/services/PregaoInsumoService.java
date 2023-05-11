package com.ifpr.gastronomique.api.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.api.models.Insumo;
import com.ifpr.gastronomique.api.models.Pregao;
import com.ifpr.gastronomique.api.models.PregaoInsumo;
import com.ifpr.gastronomique.api.repositories.InsumoRepository;
import com.ifpr.gastronomique.api.repositories.PregaoInsumoRepository;

@Service
public class PregaoInsumoService {
	
	@Autowired
	private PregaoInsumoRepository repository;
	
	@Autowired
	private InsumoRepository insumoRepository;
	
	public List<PregaoInsumo> listarInsumosDoPregao(Long idPregao) {
		var pregao = new Pregao();
		pregao.setId(idPregao);
		return repository.findByPregao(pregao);
	}
	
	public ResponseEntity<PregaoInsumo> buscarPregaoInsumoPorId(Long pregaoInsumoId) {
		var pregaoInsumo = repository.findById(pregaoInsumoId).orElse(null);
		if(pregaoInsumo != null) {
			return new ResponseEntity<PregaoInsumo>(pregaoInsumo, HttpStatus.OK);
		}
		return new ResponseEntity<PregaoInsumo>(HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<PregaoInsumo> inserirPregaoInsumo(PregaoInsumo pregaoInsumo) {
		repository.save(pregaoInsumo);
		return new ResponseEntity<PregaoInsumo>(pregaoInsumo, HttpStatus.CREATED);
	}
	
	public ResponseEntity<PregaoInsumo> alterarPregaoInsumo(Long id, PregaoInsumo pregaoInsumoAlterado) {
		var pregaoInsumo = repository.findById(pregaoInsumoAlterado.getId()).orElse(null);
		if(pregaoInsumo != null) {
			repository.save(pregaoInsumo);
			return new ResponseEntity<PregaoInsumo>(pregaoInsumo, HttpStatus.OK);
		}
		
		return new ResponseEntity<PregaoInsumo>(pregaoInsumo, HttpStatus.NOT_FOUND);
		
	}
	
	public ResponseEntity<PregaoInsumo> excluirPregaoInsumoPorId(Long id) {
		Optional<PregaoInsumo> pregaoInsumo = repository.findById(id);
		if(pregaoInsumo.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}	
	}
	
	public ResponseEntity<PregaoInsumo> listarUltimoPregaoInsumo(Long idInsumo) {
		Insumo insumo = insumoRepository.findById(idInsumo).orElseThrow(() -> new ObjectNotFoundException(idInsumo, "Insumo"));
		List<PregaoInsumo> pregoesInsumo = repository.findByInsumoOrderByPregaoDesc(insumo);
		if(pregoesInsumo.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return new ResponseEntity<PregaoInsumo>(pregoesInsumo.get(0), HttpStatus.OK);
	}
}
