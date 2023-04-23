package com.ifpr.gastronomique.api.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.api.dto.AulaDto;
import com.ifpr.gastronomique.api.models.Aula;
import com.ifpr.gastronomique.api.models.Disciplina;
import com.ifpr.gastronomique.api.models.Laboratorio;
import com.ifpr.gastronomique.api.repositories.AulaRepository;
import com.ifpr.gastronomique.api.repositories.DisciplinaRepository;
import com.ifpr.gastronomique.api.repositories.LaboratorioRepository;
import com.ifpr.gastronomique.security.models.User;
import com.ifpr.gastronomique.security.repositories.UserRepository;

@Service
public class AulaService {

	@Autowired
	private AulaRepository aulaRepository;
	
	@Autowired 
	private UserRepository usuarioRepository;
	
	@Autowired 
	private DisciplinaRepository disciplinaRepository;
	
	@Autowired 
	private LaboratorioRepository laboratorioRepository;
	
	public List<AulaDto> listarTodasAulas() {
		List<Aula> aulas = aulaRepository.findAll();
		return aulas.stream().map( a -> {
			AulaDto aulaDto = new AulaDto();
			aulaDto.setId(a.getId());
			aulaDto.setDescricao(a.getDescricao());
			aulaDto.setDataUtilizacao(a.getDataUtilizacao());
			aulaDto.setValor(a.getValor());
			aulaDto.setNomeDisciplina(a.getDisciplina().getNome());
			aulaDto.setNomeLaboratorio(a.getLaboratorio().getNome());
			aulaDto.setNomeUsuario(a.getUsuario().getFullName());
			return aulaDto;
		}).collect(Collectors.toList());
	}
	
	public ResponseEntity<Aula> buscarAulaPorId(Long aulaId) {
		Aula aula = aulaRepository.findById(aulaId).orElse(null);
		if(aula != null) {
			return new ResponseEntity<Aula>(aula, HttpStatus.OK);
		}
		return new ResponseEntity<Aula>(HttpStatus.NOT_FOUND);
	}
	
	 public ResponseEntity<AulaDto> inserirAula(Aula aula) {
		 Optional<User> usuario = usuarioRepository.findById(aula.getUsuario().getId());
		 Optional<Disciplina> disciplina = disciplinaRepository.findById(aula.getDisciplina().getId());
		 Optional<Laboratorio> laboratorio = laboratorioRepository.findById(aula.getLaboratorio().getId());
		 AulaDto aulaDto = new AulaDto();
		 aulaDto.setId(aula.getId());
		 aulaDto.setDescricao(aula.getDescricao());
		 aulaDto.setDataUtilizacao(aula.getDataUtilizacao());
		 aulaDto.setValor(aula.getValor());
		 aulaDto.setNomeDisciplina(disciplina.get().getNome());
		 aulaDto.setNomeLaboratorio(laboratorio.get().getNome());
		 aulaDto.setNomeUsuario(usuario.get().getFullName());
		 aulaRepository.save(aula);
		 return new ResponseEntity<AulaDto>(aulaDto, HttpStatus.CREATED);
	 }
	 
	 public ResponseEntity<Aula> alterarAula(Long id, Aula aulaAlterada) {
		Aula aula = aulaRepository.findById(aulaAlterada.getId()).orElse(null);
		if(aula != null) {
			aulaRepository.save(aulaAlterada);
			return new ResponseEntity<Aula>(aula, HttpStatus.OK);
		}	
		return new ResponseEntity<Aula>(aula, HttpStatus.NOT_FOUND);
	 }
	 
	 public ResponseEntity<Aula> excluirAulaPorId(Long id) {
		Optional<Aula> aula = aulaRepository.findById(id);
		if(aula.isPresent()) {
			aulaRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}	
	 }
}
