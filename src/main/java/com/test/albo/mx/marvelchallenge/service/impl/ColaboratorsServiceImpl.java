package com.test.albo.mx.marvelchallenge.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.test.albo.mx.marvelchallenge.dto.ColaboratorsDto;
import com.test.albo.mx.marvelchallenge.enums.ColaboratorRole;
import com.test.albo.mx.marvelchallenge.exception.GenericNotFoundException;
import com.test.albo.mx.marvelchallenge.repository.CharactersRepository;
import com.test.albo.mx.marvelchallenge.service.ColaboratorsService;

@Service
public class ColaboratorsServiceImpl implements ColaboratorsService {

	private CharactersRepository charactersRepository;

	public ColaboratorsServiceImpl(CharactersRepository charactersRepository) {
		this.charactersRepository = charactersRepository;
	}

	/**
	 * Esta metodo se usa para obtener desde la bd el listado de colaboradores que
	 * ha tenido el personaje en sus comics
	 * 
	 * String characterName: Corresponde al nombre del personaje
	 * 
	 * retrun ColaboratorDTO: clase que representa el objeto de colaboradores
	 */
	@Override
	public ColaboratorsDto getColaborators(String characterName) {
		ColaboratorsDto response = new ColaboratorsDto();

		var result = charactersRepository.findByShortName(characterName);

		if (result.isPresent()) {
			var character = result.get();

			List<String> writers = new ArrayList<>();
			List<String> colorists = new ArrayList<>();
			List<String> editors = new ArrayList<>();

			/**
			 * Se obtienen los comics donde ha participado el caracter (heroe) y de ahi se
			 * obtienen los distintos tipos de colaboradores
			 */
			character.getComics().stream().forEach(comic -> {
				comic.getColaborators().stream().forEach(colaborator -> {
					String name = colaborator.getName();
					String role = colaborator.getRole();
					if (role.equals(ColaboratorRole.COLORIST.name()) && !colorists.contains(name)) {
						colorists.add(name);
					} else if (role.equals(ColaboratorRole.WRITER.name()) && !writers.contains(name)) {
						writers.add(name);
					} else if (role.equals(ColaboratorRole.EDITOR.name()) && !editors.contains(name)) {
						editors.add(name);
					}
				});
			});

			/**
			 * Se asigna el valor correspondiente a cada atributo en el objeto que obtendra
			 * el cliente
			 */
			response.setLastSync(character.getLastSync());
			response.setWriters(writers);
			response.setColorists(colorists);
			response.setEditors(editors);
		} else {
			// Si no existe el carcater se dispara excepcion
			throw new GenericNotFoundException();
		}

		return response;
	}

}
