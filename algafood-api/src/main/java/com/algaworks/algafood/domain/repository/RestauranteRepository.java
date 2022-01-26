package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepository {
	List<Restaurante> listar();
	Restaurante bucar(Long id);
	Restaurante adicionar(Restaurante restaurante);
	void remove(Restaurante restaurante);
}
