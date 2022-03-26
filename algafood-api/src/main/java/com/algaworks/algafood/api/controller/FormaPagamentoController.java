package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping( value = "/formasPagamento", produces = MediaType.APPLICATION_JSON_VALUE ) // produces JSON format as default output 
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;	
	
	@Autowired
	private CadastroFormaPagamentoService formaPagamentoService;
	
	@GetMapping
	public List<FormaPagamento> listar() {
		return formaPagamentoRepository.findAll();
	}

	
	@GetMapping( "/{formaPagamentoId}" ) 
	public ResponseEntity<FormaPagamento> buscar( @PathVariable Long formaPagamentoId ) {
		Optional<FormaPagamento> optFormaPagamento =  formaPagamentoRepository.findById( formaPagamentoId );
		
		if( optFormaPagamento.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(optFormaPagamento.get() );
	}
	
	@PostMapping
	@ResponseStatus( HttpStatus.CREATED)
	public FormaPagamento adicionar( @RequestBody FormaPagamento formaPagamento ) {
		
		return formaPagamentoService.salvar(formaPagamento);
	
	}

	@PutMapping(path = "/{formaPagamentoId}")	
	public ResponseEntity<FormaPagamento> atualizar( @PathVariable Long formaPagamentoId, @RequestBody FormaPagamento formaPagamento ) {
		
		Optional<FormaPagamento> optFormaPagamento = formaPagamentoRepository.findById( formaPagamentoId );
		
		if( optFormaPagamento.isPresent() ) {			
			FormaPagamento atualFormaPagamento = optFormaPagamento.get();
			BeanUtils.copyProperties(formaPagamento, atualFormaPagamento, "id");
			FormaPagamento formaPagamentoSalva =  formaPagamentoService.salvar(atualFormaPagamento);
			return ResponseEntity.ok( formaPagamentoSalva );
		}
		
		return ResponseEntity.notFound().build();	
	}
	
	
	@DeleteMapping(path = "/{formaPagamentoId}")
	public ResponseEntity<FormaPagamento> remover( @PathVariable Long formaPagamentoId ) {
		
		try {
			
			formaPagamentoService.excluir(formaPagamentoId);
			return ResponseEntity.noContent().build(); 
		
		} catch ( EntidadeNaoEncontradaException e ) {
			
			return ResponseEntity.notFound().build();
		
		}
		
	}
	
	
	
}
