package com.test.albo.mx.marvelchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
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

		when(charactersRepository.findByShortNameAndSync("ironman", true)).thenReturn(Optional.of(getMockCharacters()));

		var characters = charactersService.getCharacters("ironman");

		assertNotNull(characters);
		assertNotNull(characters.getLastSync());
		assertEquals(characters.getCharacters().size(), 2);
		assertEquals(characters.getCharacters().get(0).getComics().size(), 2);
		assertEquals(characters.getCharacters().get(1).getComics().size(), 1);

	}

	@Test
	public void findCharacters_returnNotFound() throws Exception {
		when(charactersRepository.findByShortNameAndSync("ironman", true)).thenReturn(Optional.empty());
		assertThrows(GenericNotFoundException.class, () -> charactersService.getCharacters("ironman"));
	}

	private Characters getMockCharacters() {

		var characters1 = new ArrayList<Characters>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Characters("char1", "Character1", 123L, true, null));
			}
		};

		var characters2 = new ArrayList<Characters>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Characters("char1", "Character1", 123L, true, null));
				add(new Characters("part2", "Partner2", 321L, true, null));
				add(new Characters("part3", "Partner3", 789L, true, null));
			}
		};

		var characters3 = new ArrayList<Characters>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Characters("char1", "Character1", 123L, true, null));
				add(new Characters("part3", "Partner3", 789L, true, null));
			}
		};

		var comic1 = new Comics("Comic1");
		comic1.setCharacters(new HashSet<Characters>(characters1));
		var comic2 = new Comics("Comic2");
		comic2.setCharacters(new HashSet<Characters>(characters2));
		var comic3 = new Comics("Comic3");
		comic3.setCharacters(new HashSet<Characters>(characters3));

		var comics = new ArrayList<Comics>();
		comics.add(comic1);
		comics.add(comic2);
		comics.add(comic3);

		var characters = new Characters();
		characters.setId(1L);
		characters.setShortName("char1");
		characters.setFullName("Character1");
		characters.setMarvelId(1234L);
		characters.setLastSync(LocalDateTime.now());
		characters.setComics(new HashSet<Comics>(comics));

		return characters;
	}

}
