package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@RestController
@RequestMapping( value = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE ) // produces JSON format as default output 
public class PermissaoController {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@GetMapping
	public List<Permissao> listar() {
		return permissaoRepository.listar();
	}

	@GetMapping( produces = MediaType.APPLICATION_XML_VALUE ) // produces xml instead of default JSON format 
	public List<Permissao> listarXml() {
		return permissaoRepository.listar();
	}
	
	@GetMapping( "/{permissaoId}" ) 
	public Permissao buscar( @PathVariable Long permissaoId ) {
		return permissaoRepository.buscar( permissaoId );
	}

	
}
