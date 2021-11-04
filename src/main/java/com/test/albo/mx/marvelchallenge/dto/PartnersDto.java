package com.test.albo.mx.marvelchallenge.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(value = { "character", "comics" }, alphabetic = false)
@Schema(description = "Esquema que representa el personaje que han colaborado con el heroe")
public class PartnersDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "nombre de personaje", example = "Black Phanter")
	@JsonProperty("character")
	private String name;

	@Schema(description = "Listado de nombre de los comics en los que el personaje ha colaborado con el heroe")
	private List<String> comics;
}
