package com.algaworks.algafood.domain.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Repository
public class PermissaoRepositoryImpl implements PermissaoRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Permissao> todas() {
		return manager.createQuery("from Permissao", Permissao.class).getResultList();
	}
	
	@Transactional
	@Override
	public Permissao adicionar( Permissao permissao ) {
		return manager.merge( permissao );
	}
	
	@Override
	public Permissao porId( Long id) {
		return manager.find(Permissao.class, id);
	}
	
	@Transactional
	@Override
	public void remover( Permissao permissao) {
		permissao = porId( permissao.getId() );
		manager.remove( permissao );
	}

}
