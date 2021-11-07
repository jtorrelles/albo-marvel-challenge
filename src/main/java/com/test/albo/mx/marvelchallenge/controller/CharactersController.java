package com.test.albo.mx.marvelchallenge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.albo.mx.marvelchallenge.dto.CharactersDto;
import com.test.albo.mx.marvelchallenge.service.CharactersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("characters")
public class CharactersController {

	private CharactersService charactersServices;

	public CharactersController(CharactersService charactersService) {
		this.charactersServices = charactersService;
	}

	@Operation(summary = "Obtener listado personjes y comics asociados al heroe", description = "Personajes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Personajes encontrados", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CharactersDto.class)) }),
			@ApiResponse(responseCode = "404", description = "Super heroes no encontrados", content = @Content) })
	@GetMapping("/{character}")
	public ResponseEntity<CharactersDto> getCharacters(@PathVariable("character") String characterName) {
		log.info("reciendo request para obtener personajes del heroe {}", characterName);
		return new ResponseEntity<>(charactersServices.getCharacters(characterName), HttpStatus.OK);
	}
}
