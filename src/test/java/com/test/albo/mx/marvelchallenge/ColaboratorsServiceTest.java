package com.test.albo.mx.marvelchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
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

import com.test.albo.mx.marvelchallenge.enums.ColaboratorRole;
import com.test.albo.mx.marvelchallenge.exception.GenericNotFoundException;
import com.test.albo.mx.marvelchallenge.model.Characters;
import com.test.albo.mx.marvelchallenge.model.Colaborators;
import com.test.albo.mx.marvelchallenge.model.Comics;
import com.test.albo.mx.marvelchallenge.repository.CharactersRepository;
import com.test.albo.mx.marvelchallenge.service.ColaboratorsService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ColaboratorsService.class)
public class ColaboratorsServiceTest {

	@MockBean
	private CharactersRepository charactersRepository;

	@Autowired
	private ColaboratorsService colaboratorService;

	@Test
	public void findColaborators_returnColaboratorsInfo() throws Exception {
		when(charactersRepository.findByShortName(anyString())).thenReturn(Optional.of(getMockCharacters()));

		var colaborators = colaboratorService.getColaborators(anyString());

		assertNotNull(colaborators);
		assertNotNull(colaborators.getLastSync());
		assertEquals(colaborators.getColorists().size(), 3);
		assertEquals(colaborators.getWriters().size(), 3);
		assertEquals(colaborators.getEditors().size(), 3);

	}

	@Test
	public void findColaborators_returnNotFound() throws Exception {
		when(charactersRepository.findByShortName(anyString())).thenReturn(Optional.empty());
		assertThrows(GenericNotFoundException.class, () -> colaboratorService.getColaborators(anyString()));
	}

	private Characters getMockCharacters() {
		var colaboratorsComic1 = new ArrayList<Colaborators>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Colaborators(1L, "Colaborator1", ColaboratorRole.EDITOR.name()));
				add(new Colaborators(2L, "Colaborator2", ColaboratorRole.WRITER.name()));
				add(new Colaborators(3L, "Colaborator3", ColaboratorRole.COLORIST.name()));
				add(new Colaborators(4L, "Colaborator4", ColaboratorRole.EDITOR.name()));
			}
		};

		var colaboratorsComic2 = new ArrayList<Colaborators>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Colaborators(1L, "Colaborator1", ColaboratorRole.EDITOR.name()));
				add(new Colaborators(2L, "Colaborator2", ColaboratorRole.WRITER.name()));
				add(new Colaborators(5L, "Colaborator5", ColaboratorRole.WRITER.name()));
				add(new Colaborators(6L, "Colaborator6", ColaboratorRole.EDITOR.name()));
			}
		};

		var colaboratorsComic3 = new ArrayList<Colaborators>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Colaborators(7L, "Colaborator7", ColaboratorRole.WRITER.name()));
				add(new Colaborators(8L, "Colaborator8", ColaboratorRole.COLORIST.name()));
				add(new Colaborators(9L, "Colaborator9", ColaboratorRole.COLORIST.name()));
				add(new Colaborators(1L, "Colaborator1", ColaboratorRole.EDITOR.name()));
			}
		};

		var comic1 = new Comics("Comic1");
		comic1.setColaborators(new HashSet<Colaborators>(colaboratorsComic1));
		var comic2 = new Comics("Comic2");
		comic2.setColaborators(new HashSet<Colaborators>(colaboratorsComic2));
		var comic3 = new Comics("Comic3");
		comic3.setColaborators(new HashSet<Colaborators>(colaboratorsComic3));

		var comics = new ArrayList<Comics>();
		comics.add(comic1);
		comics.add(comic2);
		comics.add(comic3);

		var characters = new Characters();
		characters.setId(1L);
		characters.setShortName("char1");
		characters.setFullName("Character 1");
		characters.setMarvelId(1234L);
		characters.setLastSync(LocalDateTime.now());
		characters.setComics(new HashSet<Comics>(comics));

		return characters;
	}
}
