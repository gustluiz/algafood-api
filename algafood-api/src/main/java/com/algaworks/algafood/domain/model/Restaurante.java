package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	
	private String nome;
	
	@Column
	private BigDecimal taxaFrete;
	
	@ManyToOne
	@JoinColumn( name = "cozinha_id") //This line shows how to map to the foreign key to a column different from the default PK of the foreign table 
	private Cozinha cozinha;

}
