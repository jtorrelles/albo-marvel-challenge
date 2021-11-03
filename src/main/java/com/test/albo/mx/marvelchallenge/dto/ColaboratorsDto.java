package com.test.albo.mx.marvelchallenge.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ColaboratorsDto {

	@DateTimeFormat(pattern = "dd/mm/yyyy hh:mm:ss")
	private LocalDateTime last_sync;
	private List<String> editors;
	private List<String> writers;
	private List<String> colorists;
}
