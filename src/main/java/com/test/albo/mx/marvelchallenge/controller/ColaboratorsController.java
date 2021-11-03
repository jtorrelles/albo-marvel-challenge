package com.test.albo.mx.marvelchallenge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("colaborators")
public class ColaboratorsController {

	@GetMapping("/{name}")
	public ResponseEntity<HttpStatus> getColaborators() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
