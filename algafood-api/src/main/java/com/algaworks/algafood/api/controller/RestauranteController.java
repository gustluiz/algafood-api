package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
@RequestMapping( path = "/restaurantes")
public class RestauranteController {
	
	@Autowired
	RestauranteRepository restauranteRepository;
	
	@Autowired
	CadastroRestauranteService cadastroRestauranteService;
	
	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.listar();
	}
	
	@GetMapping( path = "/{restauranteId}")
	public ResponseEntity<Restaurante> buscar( @PathVariable Long restauranteId ) {
		
		Restaurante restaurante = restauranteRepository.buscar( restauranteId );
		
		if( restaurante != null ) {
			return ResponseEntity.ok(restaurante);					
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar( @RequestBody Restaurante restauranteNovo ) {
		
		try {
			Restaurante restaurante = cadastroRestauranteService.salvar( restauranteNovo );
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
			
		} catch ( EntidadeNaoEncontradaException e ) {
			//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( e.getMessage() );
			return ResponseEntity.badRequest().body( e.getMessage() );
		}
		
	}
	

	@PutMapping( path = "/{restauranteId}")
	public ResponseEntity<?> atualizar( @PathVariable Long restauranteId, @RequestBody Restaurante restauranteNovo ) {
		
		Restaurante restauranteAtual = restauranteRepository.buscar( restauranteId );
		
		if( restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(restauranteNovo, restauranteAtual, "id");
		
		try {
			Restaurante restaurante = cadastroRestauranteService.salvar( restauranteAtual );
			return ResponseEntity.status(HttpStatus.OK).body(restaurante);
			
		} catch ( EntidadeNaoEncontradaException e ) {
			return ResponseEntity.badRequest().body( e.getMessage() );
		}
				
	}
	
	@PatchMapping(path = "/{restauranteId}")
	public ResponseEntity<Restaurante> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> dados) {

		Restaurante restauranteAtual = restauranteRepository.buscar(restauranteId);

		if (restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}

		merge(dados, restauranteAtual);

		atualizar(restauranteId, restauranteAtual);

		return ResponseEntity.ok(restauranteAtual);

	}
	
	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {

		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
		
		dadosOrigem.forEach( (nomePropriedade, valor) -> {
			
			Field field = ReflectionUtils.findField( Restaurante.class, nomePropriedade );
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
			System.out.println("Campo: " + nomePropriedade + "=" + valor + "= (object) " + novoValor );
			
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
						
		});
	}
	
	

}
