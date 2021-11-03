package com.test.albo.mx.marvelchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ColaboratorsControllerExceptionHandler {

	@ExceptionHandler(ColaboratorsNoContentException.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String colaboratorNoContent(ColaboratorsNoContentException exception) {
		return null;
	}
}
