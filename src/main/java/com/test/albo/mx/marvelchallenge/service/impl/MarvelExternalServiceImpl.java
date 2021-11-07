package com.test.albo.mx.marvelchallenge.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.test.albo.mx.marvelchallenge.enums.ColaboratorRole;
import com.test.albo.mx.marvelchallenge.model.Characters;
import com.test.albo.mx.marvelchallenge.model.Colaborators;
import com.test.albo.mx.marvelchallenge.model.Comics;
import com.test.albo.mx.marvelchallenge.repository.CharactersRepository;
import com.test.albo.mx.marvelchallenge.repository.ColaboratorsRepository;
import com.test.albo.mx.marvelchallenge.repository.ComicsRepository;
import com.test.albo.mx.marvelchallenge.service.MarvelExternalService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MarvelExternalServiceImpl implements MarvelExternalService {

	@Value("${endpoint.url.external}")
	private String externalApiUrl;

	@Value("${api.external.publickey}")
	private String publicKey;

	@Value("${api.external.privatekey}")
	private String privateKey;

	private RestTemplate restTemplate;

	private ComicsRepository comicRepository;

	private CharactersRepository characterRepository;

	private ColaboratorsRepository colaboratorRepository;

	public MarvelExternalServiceImpl(RestTemplate restTemplate, ComicsRepository comicRepository,
			CharactersRepository characterRepository, ColaboratorsRepository colaboratorRepository) {
		this.restTemplate = restTemplate;
		this.comicRepository = comicRepository;
		this.characterRepository = characterRepository;
		this.colaboratorRepository = colaboratorRepository;
	}

	@PostConstruct
	private void setUp() {
		this.synchronizeData();
	}

	/**
	 * Metodo que se encarga de ejecutar la llamada a la api de marvel y actualizar
	 * la bd deacuerdo a esa informacion
	 */
	@Override
	@Scheduled(cron = "${app.periodical.expression}")
	public void synchronizeData() {
		log.info("iniciando proceso de sincronizacion....");
		log.info("momento de ejecucion {}....", LocalDateTime.now());

		// se genera el hash para impactar la api externa
		var timestampValue = Timestamp.valueOf(LocalDateTime.now()).getTime();
		var hash = String.format("%s%s%s", timestampValue, privateKey, publicKey);
		var md5Hash = DigestUtils.md5Hex(hash);

		log.debug("se genera hash {} con los valores timestamp {}", md5Hash, timestampValue);

		List<Characters> syncCharacters = characterRepository.findBySync(true);
		log.info("list {}", syncCharacters);
		for (Characters characters : syncCharacters) {
			// se ejecuta la llamada a la pai externa
			getMarvelApi(characters);
			// se actualiza el personaje con la ultima fecha de ejecucion
			characters.setLastSync(LocalDateTime.now());
			characterRepository.save(characters);
		}

	}

	/**
	 * Metodo que se encarga de ejecutar la llamada a la api externa con los datos
	 * especificos del persoanje
	 * 
	 * @param character Instancia del entity correspondiente al personaje
	 */
	private void getMarvelApi(Characters character) {

		var timestampValue = Timestamp.valueOf(LocalDateTime.now());
		var hash = String.format("%s%s%s", timestampValue, privateKey, publicKey);
		var md5Hash = DigestUtils.md5Hex(hash);

		log.info("hash {}", md5Hash);

		JsonNode response;

		try {
			log.info("character {}", character.getFullName());

			response = restTemplate.getForObject(externalApiUrl, JsonNode.class, character.getMarvelId(),
					timestampValue, publicKey, md5Hash);

			log.debug("api response {}", response);

			/**
			 * Se valida que la respuesta de la api externa contenga los elementos de
			 * busqueda de la informacion
			 */
			if (response.has("data") && response.get("data").has("results")) {

				var comics = response.get("data").get("results");
				// se itera los resultados (comics) de la api
				comics.forEach(comic -> {
					var title = comic.get("title").textValue();
					// se verifica si el comic existe en la bd
					Comics comicDb;
					var optionalComic = comicRepository.findByName(title);
					if (!optionalComic.isPresent()) {
						comicDb = comicRepository.save(new Comics(title));
					} else {
						comicDb = optionalComic.get();
					}

					// se insertan los personajes del comic al objeto de persistencia
					comicDb = insertCharacters(comic.get("characters").get("items"), comicDb);
					// se insertan los colaboradores del comic al objeto de persistencia
					comicDb = insertColaborators(comic.get("creators").get("items"), comicDb);

					// se guardan los datos en la bd
					comicRepository.save(comicDb);
				});
			} else {
				log.error("No es posible interpretar la respuesta de la api, {}", response.toString());
			}
		} catch (RestClientException exception) {
			log.error("error {}", exception.getMessage());
		}
	}

	/**
	 * Metodo que se encarga de insertar los personajes en comunes en el comic
	 * 
	 * @param characters representacion del objecto que contiene el listado de
	 *                   personajes del comic
	 * @param comicDb    instancia del entity correspondiente al comic
	 * @return instancia del entity del comic con el listado de personajes
	 */
	private Comics insertCharacters(JsonNode characters, Comics comicDb) {

		log.debug("listado de personajes a procesar {}", characters.toString());

		characters.forEach(character -> {
			var characterName = character.get("name").textValue();
			log.debug("procesando personaje {}, en el comic {}", characterName, comicDb.getName());
			var optionalCharacter = characterRepository.findByFullName(characterName);
			// si no existe en la bd se crea la nueva entidad
			if (!optionalCharacter.isPresent()) {
				comicDb.addCharacter(new Characters(characterName, characterName, 0L, false, null));
			} else {
				var characterDb = optionalCharacter.get();
				comicDb.addCharacter(characterDb);
			}
		});

		return comicDb;
	}

	/**
	 * Metodo que se encarga de insertar los colaboradores en comunes en el comic
	 * que contenga los roles EDITOR, WRITER o COLORIST
	 * 
	 * @param creators representacion del objecto que contiene el listado de
	 *                 colaboradores del comic
	 * @param comicDb  instancia del entity correspondiente al comic
	 * @return instancia del entity del comic con el listado de colaboradores
	 */
	private Comics insertColaborators(JsonNode creators, Comics comicDb) {

		log.debug("listado de colaboradores a procesar {}", creators.toString());

		String[] roles = { ColaboratorRole.COLORIST.name(), ColaboratorRole.WRITER.name(),
				ColaboratorRole.EDITOR.name() };

		creators.forEach(creator -> {
			var name = creator.get("name").textValue();
			var role = creator.get("role").textValue().toUpperCase().trim();

			if (Arrays.stream(roles).anyMatch(role::contains)) {
				log.debug("procesando colaborador {}, l rol {} en el comic {}", name, role, comicDb.getName());
				var internalRole = getRole(role);
				var optionalColaborator = colaboratorRepository.findByNameAndRole(name, internalRole);
				// si no existe en la bd se crea la nueva entidad
				if (!optionalColaborator.isPresent()) {
					comicDb.addColaborator(new Colaborators(name, internalRole));
				} else {
					var colaboratorDb = optionalColaborator.get();
					comicDb.addColaborator(colaboratorDb);
				}
			}
		});

		return comicDb;
	}

	/**
	 * Metodo para detectar el rol del colaborador
	 * 
	 * @param apiRole representacion del rol obtenido desde la api
	 * @return cadena de caracteres del rol interno (enum) correspondiente
	 */
	private String getRole(String apiRole) {
		if (apiRole.contains(ColaboratorRole.WRITER.name())) {
			return ColaboratorRole.WRITER.name().toUpperCase();
		} else if (apiRole.contains(ColaboratorRole.EDITOR.name())) {
			return ColaboratorRole.EDITOR.name().toUpperCase();
		} else {
			return ColaboratorRole.COLORIST.name().toUpperCase();
		}
	}
}
