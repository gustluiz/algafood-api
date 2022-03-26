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
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import com.algaworks.algafood.domain.service.CadastroPermissaoService;

@RestController
@RequestMapping( value = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE ) // produces JSON format as default output 
public class PermissaoController {

	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private CadastroPermissaoService cadastroPermissaoService;

	
	@GetMapping
	public List<Permissao> listar() {
		return permissaoRepository.findAll();
	}

	@GetMapping( produces = MediaType.APPLICATION_XML_VALUE ) // produces xml instead of default JSON format 
	public List<Permissao> listarXml() {
		return permissaoRepository.findAll();
	}
	
	@GetMapping( "/{permissaoId}" ) 
	public Permissao buscar( @PathVariable Long permissaoId ) {
		return permissaoRepository.findById( permissaoId ).get();
	}
	
	
	@PostMapping
	@ResponseStatus( HttpStatus.CREATED)
	public Permissao adicionar( @RequestBody Permissao permissao ) {
		
		return cadastroPermissaoService.salvar(permissao);
	
	}
	
	@PutMapping( path = "/{permissaoId}")	
	public ResponseEntity<Permissao> atualizar( @PathVariable Long permissaoId, @RequestBody Permissao permissao ) {
		
		Optional<Permissao> optPermissao = permissaoRepository.findById(permissaoId);
		
		if( optPermissao.isPresent() ) {
			
			Permissao permissaoAtual = optPermissao.get();
			BeanUtils.copyProperties(permissao, permissaoAtual, "id");
			Permissao novaPermissao = cadastroPermissaoService.salvar(permissaoAtual);
			return ResponseEntity.ok( novaPermissao ) ;	
		}
	
		return ResponseEntity.notFound().build(); 
	}
	
	
	@DeleteMapping( path = "/{permissaoId}")
	public ResponseEntity<Permissao> remover( @PathVariable Long permissaoId ) {
		
		try {
			
			cadastroPermissaoService.excluir( permissaoId );
			return ResponseEntity.noContent().build(); 
		
		} catch ( EntidadeNaoEncontradaException e ) {
			
			return ResponseEntity.notFound().build();
		
		}
		
	}
	
}
