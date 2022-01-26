package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonRootName("cozinha")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	//@JsonIgnore // Remove filed from output
	private long id;
	
	//@JsonProperty("titulo") // Change filed name in the output
	@Column
	private String nome;

}
