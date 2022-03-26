package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository 
		extends JpaRepository<Restaurante, Long>, RestauranteRepositoryCustomQueries {
	
	// queryBy and findBy are the same
	public List<Restaurante> queryByTaxaFreteBetween( BigDecimal taxaInicial, BigDecimal taxaFinal );
	public List<Restaurante> findByTaxaFreteBetween( BigDecimal taxaInicial, BigDecimal taxaFinal );
	
	//public List<Restaurante> findByNomeContainingAndCozinhaId( String nome, Long cozinhaId );
	//@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	public List<Restaurante> consultarPorNomeAndCozinhaId( String nome, @Param("id") Long cozinhaId );
	
	public Optional<Restaurante> findFirstRestauranteByNomeContaining( String nome);

	public List<Restaurante> queryTop2RestaurantesByNomeContaining( String nome);
	
	public boolean existsByNomeContaining( String nome);
	
	public int countByCozinhaId( Long cozinhaId);
	
	public List<Restaurante> consultaPorNomeTaxaFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	
}
