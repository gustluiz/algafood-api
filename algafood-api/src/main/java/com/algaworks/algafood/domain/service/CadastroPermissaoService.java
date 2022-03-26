package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

	@Autowired
	PermissaoRepository permissaoRepository;
	
	public Permissao salvar( Permissao permissao) {
		return permissaoRepository.save(permissao);		
	}
	
	public void excluir( Long permissaoId ) {
		
		try {

			permissaoRepository.deleteById(permissaoId);			

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Permissao de código %d não pode ser encontrado", permissaoId));
			
		} 
	}
	
}
