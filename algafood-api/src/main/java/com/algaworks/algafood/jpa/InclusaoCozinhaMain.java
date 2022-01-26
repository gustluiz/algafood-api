package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

public class InclusaoCozinhaMain {
	
	public static void main(String[] args) {
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		CozinhaRepository cozinhas = applicationContext.getBean(CozinhaRepository.class);

		Cozinha cozinha1 = new Cozinha( );
		cozinha1.setNome("Italiana");
		cozinha1 = cozinhas.salvar( cozinha1 );
		System.out.println( cozinha1.getId() + "|" + cozinha1.getNome() );
		
		Cozinha cozinha2 = new Cozinha( );
		cozinha2.setNome("Japonesa");		
		cozinha2 = cozinhas.salvar( cozinha2 );		
		System.out.println( cozinha2.getId() + "|" + cozinha2.getNome() );
	}
	
}
