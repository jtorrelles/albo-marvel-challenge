package com.test.albo.mx.marvelchallenge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.albo.mx.marvelchallenge.dto.ColaboratorsDto;
import com.test.albo.mx.marvelchallenge.service.ColaboratorsService;

@RestController
@RequestMapping("colaborators")
public class ColaboratorsController {

	private ColaboratorsService colaboratorService;

	public ColaboratorsController(ColaboratorsService colaboratorService) {
		this.colaboratorService = colaboratorService;
	}

	@GetMapping("/{character}")
	public ResponseEntity<ColaboratorsDto> getColaborators(@PathVariable("character") String characterName) {
		var colaborators = colaboratorService.getColaborators(characterName);
		return new ResponseEntity<>(colaborators, HttpStatus.OK);
	}
}
