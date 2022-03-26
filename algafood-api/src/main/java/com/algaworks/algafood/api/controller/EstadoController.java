package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/estados", produces = MediaType.APPLICATION_JSON_VALUE) // produces JSON format as default
																					// output
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstadoService;

	@GetMapping
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}

	@GetMapping(path = "/{estadoId}")
	public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) {

		Optional<Estado> optEstado = estadoRepository.findById(estadoId);

		if (optEstado.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok( optEstado.get() );

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody Estado estado) {
		return cadastroEstadoService.salvar(estado);
	}

	@PutMapping(path = "/{estadoId}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long estadoId, @RequestBody Estado estado) {

		Optional<Estado> optEstadoAtual = estadoRepository.findById(estadoId);

		if ( optEstadoAtual.isPresent() ) {

			Estado estadoAtual = optEstadoAtual.get();
			
			// Copy all attributes from estado to estadoAtual except the "id"
			BeanUtils.copyProperties(estado, estadoAtual, "id");

			cadastroEstadoService.salvar(estadoAtual);

			return ResponseEntity.ok(estadoAtual);
		}

		return ResponseEntity.notFound().build();

	}

	@DeleteMapping(path = "/{estadoId}")
	public ResponseEntity<Estado> remover(@PathVariable Long estadoId) {

		try {
			cadastroEstadoService.excluir(estadoId);
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();

		}
	}

	@PatchMapping(path = "/{estadoId}")
	public ResponseEntity<Estado> atualizarParcial(@PathVariable Long estadoId, @RequestBody Map<String, Object> dados) {

		Optional<Estado> optEstadoAtual = estadoRepository.findById(estadoId);

		if ( optEstadoAtual.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}

		Estado estadoAtual = optEstadoAtual.get();
		
		merge(dados, estadoAtual);

		atualizar(estadoId, estadoAtual);

		return ResponseEntity.ok(estadoAtual);

	}

	private void merge(Map<String, Object> dadosOrigem, Estado estadoDestino) {

		ObjectMapper objectMapper = new ObjectMapper();
		Estado estadoOrigem = objectMapper.convertValue(dadosOrigem, Estado.class);
		
		dadosOrigem.forEach( (nomePropriedade, valor) -> {
			
			Field field = ReflectionUtils.findField( Estado.class, nomePropriedade );
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, estadoOrigem);
			
			System.out.println("Campo: " + nomePropriedade + "=" + valor + "= (object) " + novoValor );
						
			ReflectionUtils.setField(field, estadoDestino, novoValor);			
			
		});
	}

}
