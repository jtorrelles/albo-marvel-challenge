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
		assertEquals(characters.getCharacters().size(), 2);

	}

	@Test
	public void findCharacters_returnNotFound() throws Exception {
		when(charactersRepository.findByShortName(anyString())).thenReturn(Optional.empty());
		assertThrows(GenericNotFoundException.class, () -> charactersService.getCharacters(anyString()));
	}

	private Characters getMockCharacters() {

		var characters1 = new ArrayList<Characters>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Characters(1L, "char1", "Character1", 123L, true, null, null));
			}
		};

		var characters2 = new ArrayList<Characters>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Characters(1L, "char1", "Character1", 123L, true, null, null));
				add(new Characters(2L, "part2", "Partner2", 321L, true, null, null));
				add(new Characters(3L, "part3", "Partner3", 789L, true, null, null));
			}
		};

		var characters3 = new ArrayList<Characters>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Characters(1L, "char1", "Character1", 123L, true, null, null));
				add(new Characters(3L, "part3", "Partner3", 789L, true, null, null));
			}
		};

		var comics = new ArrayList<Comics>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Comics(1L, "Comic1", null, characters1));
				add(new Comics(2L, "Comic2", null, characters2));
				add(new Comics(3L, "Comic3", null, characters3));
			}
		};

		var characters = new Characters();
		characters.setId(1L);
		characters.setShortName("char1");
		characters.setFullName("Character1");
		characters.setMarvelId(1234L);
		characters.setLastSync(LocalDateTime.now());
		characters.setComics(comics);

		return characters;
	}

}
