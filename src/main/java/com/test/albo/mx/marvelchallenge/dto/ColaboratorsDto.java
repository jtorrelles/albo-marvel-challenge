package com.test.albo.mx.marvelchallenge.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(value = { "last_sync", "editors", "writers", "colorists" }, alphabetic = false)
@Schema(description = "Esquema que respresenta los colaboradores de los comics donde ha aprecido el heroe")
public class ColaboratorsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	@JsonProperty("last_sync")
	@Schema(description = "Fecha de ultima sincronizacion con la fuente de Marvel en formato dd/MM/yyy hh:mm:ss", example = "04/11/2021 12:00:00")
	private LocalDateTime lastSync;

	@Schema(description = "listado de nombres de los coloborador en rol de editor")
	private List<String> editors;
	@Schema(description = "listado de nombres de los coloborador en rol de escritor")
	private List<String> writers;
	@Schema(description = "listado de nombres de los coloborador en rol de colorista")
	private List<String> colorists;
}
