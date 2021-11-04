package com.test.albo.mx.marvelchallenge.service;

import com.test.albo.mx.marvelchallenge.dto.CharactersDto;

public interface CharactersService {
	// obtener listado de personajes
	CharactersDto getCharacters(String characterName);
}
