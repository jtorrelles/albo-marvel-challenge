package com.test.albo.mx.marvelchallenge.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(value = { "character", "comics" }, alphabetic = false)
public class PartnersDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("character")
	private String name;

	private List<String> comics;
}
