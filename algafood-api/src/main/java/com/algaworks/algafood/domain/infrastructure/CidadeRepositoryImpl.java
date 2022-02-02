package com.algaworks.algafood.domain.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Repository
public class CidadeRepositoryImpl implements CidadeRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Cidade> listar() {
		return manager.createQuery("from Cidade", Cidade.class).getResultList();
	}
	
	@Transactional
	@Override
	public Cidade salvar( Cidade cidade ) {
		return manager.merge( cidade );
	}
	
	@Override
	public Cidade buscar( Long id) {
		return manager.find(Cidade.class, id);
	}
	
	@Transactional
	@Override
	public void remover( Long cidadeId) {
		Cidade cidade = buscar( cidadeId );
		
		if( cidade == null ) {
			throw new EmptyResultDataAccessException(1);
		}
		
		manager.remove( cidade );
	}

}
