package com.test.albo.mx.marvelchallenge.service;

import com.test.albo.mx.marvelchallenge.dto.ColaboratorsDto;

public interface ColaboratorsService {
	// obtener listado de colaboradores
	ColaboratorsDto getColaborators(String characterName);
}
