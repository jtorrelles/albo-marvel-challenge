package com.test.albo.mx.marvelchallenge;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.test.albo.mx.marvelchallenge.model.Colaborator;
import com.test.albo.mx.marvelchallenge.repository.ColaboratorsRepository;
import com.test.albo.mx.marvelchallenge.service.ColaboratorsService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ColaboratorsService.class)
public class ColaboratorsServiceTest {

	@MockBean
	private ColaboratorsRepository colaboratorsRepository;

	@Autowired
	private ColaboratorsService colaboratorService;

	@Test
	public void findColaborators_returnColaboratorsInfo() throws Exception {
		when(colaboratorsRepository.findByShortName(anyString())).thenReturn(new Colaborator());
		var colaborators = colaboratorService.getColaborators("capamerica");
		assertNotNull(colaborators);
	}
}
