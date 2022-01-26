package com.algaworks.algafood.jpa;

import java.math.BigDecimal;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class InclusaoRestauranteMain {
	
	public static void main(String[] args) {
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		RestauranteRepository restaurantes = applicationContext.getBean(RestauranteRepository.class);
		CozinhaRepository cozinhas = applicationContext.getBean(CozinhaRepository.class);

		Restaurante restaurante1 = new Restaurante();	
		
		
		restaurante1.setNome("Hambugao Espacial");
		restaurante1.setTaxaFrete( new BigDecimal(12.0) );
		restaurante1.setCozinha( cozinhas.buscar(3L) );
		restaurante1 = restaurantes.adicionar( restaurante1 );
		System.out.printf( "%s - %9.2f", restaurante1.getNome(), restaurante1.getTaxaFrete() );
		
		Restaurante restaurante2 = new Restaurante();	
		
		restaurante2.setNome("Dogao podrao");
		restaurante2.setTaxaFrete( new BigDecimal(0.0) );
		restaurante2.setCozinha( cozinhas.buscar(1L) );
		restaurante2 = restaurantes.adicionar( restaurante2 );
		System.out.printf( "%s - %9.2f", restaurante2.getNome(), restaurante2.getTaxaFrete() );
		
		restaurantes.listar().stream().forEach( restaurante -> System.out.println(restaurante ) );
	}
	
}
