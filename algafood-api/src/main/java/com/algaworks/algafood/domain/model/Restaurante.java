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
	@Column( nullable = false)
	private long id;
	
	@Column( nullable = false)
	private String nome;
	
	  //nullable parameter may be used only when the JPA is responsible for creating the database, normally for development 
	  //Under production env. it shall not be used as the database may be managed externally the JPA
	@Column( nullable = false) 
	private BigDecimal taxaFrete;
	
	@ManyToOne
	@JoinColumn( name = "cozinha_id", nullable = false) //This line shows how to map to the foreign key to a column different from the default PK of the foreign table 
	private Cozinha cozinha;

}
