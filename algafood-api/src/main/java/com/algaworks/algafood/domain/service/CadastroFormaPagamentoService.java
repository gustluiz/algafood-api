package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {

	@Autowired
	FormaPagamentoRepository formaPagamentoRepository;
	
	public FormaPagamento salvar( FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);		
	}
	
	public void excluir( Long formaPagamentoId ) {
		
		try {

			formaPagamentoRepository.deleteById(formaPagamentoId);			

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("FormaPagamento de código %d não pode ser encontrado", formaPagamentoId));
			
		} 
	}
	
}
