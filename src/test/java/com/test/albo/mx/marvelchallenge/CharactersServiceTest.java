package com.test.albo.mx.marvelchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.test.albo.mx.marvelchallenge.exception.GenericNotFoundException;
import com.test.albo.mx.marvelchallenge.model.CharacterPartners;
import com.test.albo.mx.marvelchallenge.model.Characters;
import com.test.albo.mx.marvelchallenge.model.Comics;
import com.test.albo.mx.marvelchallenge.repository.CharactersRepository;
import com.test.albo.mx.marvelchallenge.service.CharactersService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CharactersService.class)
public class CharactersServiceTest {

	@MockBean
	private CharactersRepository charactersRepository;

	@Autowired
	private CharactersService charactersService;

	@Test
	public void findColaborators_returnColaboratorsInfo() throws Exception {
		when(charactersRepository.findByShortName(anyString())).thenReturn(Optional.of(getMockCharacters()));

		var characters = charactersService.getCharacters(anyString());

		assertNotNull(characters);
		assertNotNull(characters.getLastSync());
		assertEquals(characters.getCharacters().size(), 3);
		assertEquals(characters.getCharacters().get(0).getComics(), 4);
		assertEquals(characters.getCharacters().get(1).getComics().size(), 1);
		assertEquals(characters.getCharacters().get(2).getComics().size(), 2);

	}

	@Test
	public void findCharacters_returnNotFound() throws Exception {
		when(charactersRepository.findByShortName(anyString())).thenReturn(Optional.empty());
		assertThrows(GenericNotFoundException.class, () -> charactersService.getCharacters(anyString()));
	}

	private Characters getMockCharacters() {

		var comicsPartner1 = new ArrayList<Comics>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Comics(1L, "Comic1", null, null));
				add(new Comics(2L, "Comic2", null, null));
				add(new Comics(3L, "Comic3", null, null));
				add(new Comics(4L, "Comic4", null, null));
			}
		};

		var comicsPartner2 = new ArrayList<Comics>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Comics(5L, "Comic5", null, null));
			}
		};

		var comicsPartner3 = new ArrayList<Comics>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Comics(6L, "Comic6", null, null));
				add(new Comics(7L, "Comic7", null, null));
			}
		};

		var partners = new ArrayList<CharacterPartners>() {
			private static final long serialVersionUID = 1L;
			{
				add(new CharacterPartners(1L, "Partner1", comicsPartner1));
				add(new CharacterPartners(2L, "Partner2", comicsPartner2));
				add(new CharacterPartners(3L, "Partner3", comicsPartner3));
			}
		};

		var characters = new Characters();
		characters.setId(1L);
		characters.setShortName("char1");
		characters.setFullName("Character 1");
		characters.setMarvelId(1234L);
		characters.setLastSync(LocalDateTime.now());
		characters.setPartners(partners);

		return characters;
	}

}
