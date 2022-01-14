package com.algaworks.algafood.domain.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Estado> todos() {
		return manager.createQuery("from Estado", Estado.class).getResultList();
	}
	
	@Transactional
	@Override
	public Estado adicionar( Estado estado ) {
		return manager.merge( estado );
	}
	
	@Override
	public Estado porId( Long id) {
		return manager.find(Estado.class, id);
	}
	
	@Transactional
	@Override
	public void remover( Estado estado) {
		estado = porId( estado.getId() );
		manager.remove( estado );
	}

}
