package com.test.albo.mx.marvelchallenge.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.test.albo.mx.marvelchallenge.dto.CharactersDto;
import com.test.albo.mx.marvelchallenge.dto.PartnersDto;
import com.test.albo.mx.marvelchallenge.exception.GenericNotFoundException;
import com.test.albo.mx.marvelchallenge.model.Comics;
import com.test.albo.mx.marvelchallenge.repository.CharactersRepository;
import com.test.albo.mx.marvelchallenge.service.CharactersService;

@Service
public class CharactersServiceImpl implements CharactersService {

	private CharactersRepository charactersRepository;

	public CharactersServiceImpl(CharactersRepository charactersRepository) {
		this.charactersRepository = charactersRepository;
	}

	@Override
	public CharactersDto getCharacters(String characterName) {
		CharactersDto response = new CharactersDto();
		var result = charactersRepository.findByShortName(characterName);
		if (result.isPresent()) {

			var character = result.get();
			List<PartnersDto> partners = new ArrayList<>();

			/**
			 * Se ubican los partners (companeros) asociados al caracters (personaje) de la
			 * busqueda y luego por cada partner se extraen los comics en comun
			 */
			character.getPartners().stream().forEach(partner -> {
				var name = partner.getName();
				var comics = partner.getComics().stream().distinct().map(Comics::getName).collect(Collectors.toList());

				var partnerDto = new PartnersDto(name, comics);
				if (!partners.contains(partnerDto)) {
					partners.add(partnerDto);
				}
			});

			response.setCharacters(partners);
			response.setLastSync(character.getLastSync());

		} else {
			throw new GenericNotFoundException();
		}
		return response;
	}

}
