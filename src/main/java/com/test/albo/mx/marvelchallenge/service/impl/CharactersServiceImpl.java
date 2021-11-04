package com.test.albo.mx.marvelchallenge.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.test.albo.mx.marvelchallenge.dto.CharactersDto;
import com.test.albo.mx.marvelchallenge.dto.PartnersDto;
import com.test.albo.mx.marvelchallenge.exception.GenericNotFoundException;
import com.test.albo.mx.marvelchallenge.repository.CharactersRepository;
import com.test.albo.mx.marvelchallenge.service.CharactersService;

@Service
public class CharactersServiceImpl implements CharactersService {

	private CharactersRepository charactersRepository;

	public CharactersServiceImpl(CharactersRepository charactersRepository) {
		this.charactersRepository = charactersRepository;
	}

	/**
	 * Este metodo obtiene desde la bd el listado de personajes que han participado
	 * en conjunto con el heroe de busqueda, incluye el listado de comics donde han
	 * participado
	 * 
	 * String characterName: Corresponde al nombre del heroe ingresado por el
	 * usuario
	 * 
	 * return CharactersDto: COrresponde a la representacion que espera el cliente
	 * del listado de caracteres
	 */
	@Override
	public CharactersDto getCharacters(String characterName) {
		CharactersDto response = new CharactersDto();
		var result = charactersRepository.findByShortName(characterName);
		if (result.isPresent()) {

			var character = result.get();
			Map<String, List<String>> test = new HashMap<>();

			/**
			 * Se ubican los comics del caracter (personaje) y luego por cada comic se
			 * extraen los partnes (companeros) en comun, se crea un hashmap para utilizar
			 * el companero como key del set de datos.
			 * 
			 * DATO: No se incluye dentro de los datos el caracter usado para la busqueda
			 */
			character.getComics().stream().forEach(comic -> {
				var comicName = comic.getName();
				comic.getCharacters().stream().forEach(partner -> {
					var name = partner.getFullName();
					if (!name.equals(character.getFullName())) {
						if (test.containsKey(name)) {
							var comicsPartner = test.get(name);
							if (!comicsPartner.contains(comicName)) {
								comicsPartner.add(comicName);
								test.replace(name, comicsPartner);
							}
						} else {
							test.put(name, new ArrayList<String>() {
								{
									add(comicName);
								}
							});
						}
					}
				});
			});

			// Se itera el resultado para crea los objetos que representaran los datos en la
			// respuesta al usuario
			var partners = new ArrayList<PartnersDto>();
			test.forEach((partner, comics) -> {
				partners.add(new PartnersDto(partner, comics));
			});

			// se asignan los valores de los atributos correspondientes
			response.setCharacters(partners);
			response.setLastSync(character.getLastSync());

		} else {
			// si no existe el personaje ingresado por el usuario se genera un excepcion
			throw new GenericNotFoundException();
		}
		return response;
	}

}
