package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping( path = "/teste")
public class TesteController {

	@Autowired
	CozinhaRepository cozinhaRepository;

	@Autowired
	RestauranteRepository restauranteRepository;

	
	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> cozinhasPorNome(String nome ) {
		return cozinhaRepository.findTodasByNomeContaining( nome );
	}
	
	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> restaurantesPorTaxaFrete( BigDecimal  taxaInicial, BigDecimal taxaFinal ) {
		return restauranteRepository.queryByTaxaFreteBetween( taxaInicial, taxaFinal );
	}
	
	@GetMapping("/restaurantes/por-nome-e-taxa-frete")
	public List<Restaurante> restaurantesPorNomeFrete( String nome, BigDecimal  taxaInicial, BigDecimal taxaFinal ) {
		return restauranteRepository.consultaPorNomeTaxaFrete( nome, taxaInicial, taxaFinal );
	}
	
	@GetMapping("/restaurantes/por-nome-cozinhaId")
	public List<Restaurante> restaurantesPorNomeCozinhaId ( String  nome, Long cozinhaId ) {
		//return restauranteRepository.findByNomeContainingAndCozinhaId( nome, cozinhaId );
		return restauranteRepository.consultarPorNomeAndCozinhaId( nome, cozinhaId );
		
	}
	
	@GetMapping("/restaurantes/primeiro-por-nome")
	public Optional<Restaurante> restaurantesPrimeiroPorNome( String nome ) {
		return restauranteRepository.findFirstRestauranteByNomeContaining(  nome );
	}
	
	@GetMapping("/restaurantes/top2-por-nome")
	public List<Restaurante> restaurantesTop2PorNome ( String  nome ) {
		return restauranteRepository.queryTop2RestaurantesByNomeContaining( nome );
	}
	
	@GetMapping("/restaurantes/exists")
	public boolean restauranteExistsPorNome ( String  nome ) {
		return restauranteRepository.existsByNomeContaining( nome );
	}
	
	@GetMapping("/restaurantes/count-por-cozinhaId")
	public int restauranteCountByCozinhaId ( Long cozinhaId ) {
		return restauranteRepository.countByCozinhaId( cozinhaId );
	}
}
