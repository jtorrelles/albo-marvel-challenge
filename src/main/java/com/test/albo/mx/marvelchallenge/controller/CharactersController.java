package com.test.albo.mx.marvelchallenge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.albo.mx.marvelchallenge.dto.CharactersDto;
import com.test.albo.mx.marvelchallenge.service.CharactersService;

@RestController
@RequestMapping("characters")
public class CharactersController {

	private CharactersService charactersServices;

	public CharactersController(CharactersService charactersService) {
		this.charactersServices = charactersService;
	}

	@GetMapping("/{character}")
	public ResponseEntity<CharactersDto> getCharacters(@PathVariable("character") String characterName) {
		return new ResponseEntity<>(charactersServices.getCharacters(characterName), HttpStatus.OK);
	}
}
