package com.algaworks.algafood.domain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;
import lombok.NonNull;

@JacksonXmlRootElement( localName = "cozinhas")
@Data
public class CozinhaXmlWrapper {
	
	@JsonProperty( "cozinha" )
	@JacksonXmlElementWrapper( useWrapping = false)
	@NonNull
	private List<Cozinha> cozinhas;
}
