package com.test.albo.mx.marvelchallenge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.albo.mx.marvelchallenge.dto.CharactersDto;
import com.test.albo.mx.marvelchallenge.dto.ColaboratorsDto;
import com.test.albo.mx.marvelchallenge.service.ColaboratorsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("colaborators")
public class ColaboratorsController {

	private ColaboratorsService colaboratorService;

	public ColaboratorsController(ColaboratorsService colaboratorService) {
		this.colaboratorService = colaboratorService;
	}

	@Operation(summary = "Obtener listado de colaboradores en los comics asociados al heroe", description = "Colaboradores")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Colaboradores encontrados", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CharactersDto.class)) }),
			@ApiResponse(responseCode = "404", description = "Colaboradores no encontrados", content = @Content) })
	@GetMapping("/{character}")
	public ResponseEntity<ColaboratorsDto> getColaborators(@PathVariable("character") String characterName) {
		log.info("reciendo request para obtener colaboradores del heroe {}", characterName);
		return new ResponseEntity<>(colaboratorService.getColaborators(characterName), HttpStatus.OK);
	}
}
