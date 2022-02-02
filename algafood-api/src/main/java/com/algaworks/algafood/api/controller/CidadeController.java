package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping( value = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE ) // produces JSON format as default output 
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidadeService;

	
	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.listar();
	}

	@GetMapping( path = "/{cidadeId}")	
	public ResponseEntity<Cidade> buscar( @PathVariable Long cidadeId ) {
		
		Cidade cidade = cidadeRepository.buscar(cidadeId);
		
		if( cidade == null ) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(cidade);
		
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar( @RequestBody Cidade cidade ) {
		try {
		
			cidade = cadastroCidadeService.salvar(cidade);			
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		
		} catch ( EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage() );
		}		
	}
	
	@PutMapping(path = "/{cidadeId}")
	public ResponseEntity<?> atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {

		Cidade cidadeAtual = cidadeRepository.buscar(cidadeId);

		if (cidadeAtual != null) {

			// Copy all attributes from cidade to cidadeAtual except the "id"
			BeanUtils.copyProperties(cidade, cidadeAtual, "id");
			
			try {
			
				cadastroCidadeService.salvar(cidadeAtual);
				return ResponseEntity.ok(cidadeAtual);
				
			} catch ( EntidadeNaoEncontradaException e ) {
			
				return ResponseEntity.badRequest().body(e.getMessage() );				
			
			}
		}

		return ResponseEntity.notFound().build();

	}
	
	@DeleteMapping(path = "/{cidadeId}")
	public ResponseEntity<Cidade> remover(@PathVariable Long cidadeId) {

		try {
				cadastroCidadeService.excluir(cidadeId);				
				return ResponseEntity.noContent().build();

		} catch( EntidadeNaoEncontradaException e )	{	
			return ResponseEntity.notFound().build();
		
		} catch (EntidadeEmUsoException e) {					
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		
		}

	}
	
}
