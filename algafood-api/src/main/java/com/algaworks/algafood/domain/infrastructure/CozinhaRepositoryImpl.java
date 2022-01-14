package com.algaworks.algafood.domain.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Repository
public class CozinhaRepositoryImpl implements CozinhaRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Cozinha> todas() {
		return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}
	
	@Transactional
	@Override
	public Cozinha adicionar( Cozinha cozinha ) {
		return manager.merge( cozinha );
	}
	
	@Override
	public Cozinha porId( Long id) {
		return manager.find(Cozinha.class, id);
	}
	
	@Transactional
	@Override
	public void remover( Cozinha cozinha) {
		cozinha = porId( cozinha.getId() );
		manager.remove( cozinha );
	}

}
