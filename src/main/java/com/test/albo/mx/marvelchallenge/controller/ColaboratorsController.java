package com.test.albo.mx.marvelchallenge.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.albo.mx.marvelchallenge.dto.ColaboratorsDto;

@RestController
@RequestMapping("colaborators")
public class ColaboratorsController {

	@GetMapping("/{name}")
	public ResponseEntity<ColaboratorsDto> getColaborators() {
		return new ResponseEntity<>(new ColaboratorsDto(LocalDateTime.now(), new ArrayList<String>(),
				new ArrayList<String>(), new ArrayList<String>()), HttpStatus.OK);
	}
}
