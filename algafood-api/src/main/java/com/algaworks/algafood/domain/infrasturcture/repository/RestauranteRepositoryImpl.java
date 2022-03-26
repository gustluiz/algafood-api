package com.algaworks.algafood.domain.infrasturcture.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryCustomQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryCustomQueries {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurante> consultaPorNomeTaxaFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		var jpql = "from Restaurante where nome like :nome "
				+ "and taxaFrete between :taxaFreteInicial and :taxaFreteFinal";

		return manager.createQuery(jpql, Restaurante.class).setParameter("nome", "%" + nome + "%")
				.setParameter("taxaFreteInicial", taxaFreteInicial).setParameter("taxaFreteFinal", taxaFreteFinal)
				.getResultList();

	}
}
