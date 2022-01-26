package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@RestController
@RequestMapping( value = "/estados", produces = MediaType.APPLICATION_JSON_VALUE ) // produces JSON format as default output 
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@GetMapping
	public List<Estado> listar() {
		return estadoRepository.listar();
	}

	@GetMapping( produces = MediaType.APPLICATION_XML_VALUE ) // produces xml instead of default JSON format 
	public List<Estado> listarXml() {
		return estadoRepository.listar();
	}

	
}
