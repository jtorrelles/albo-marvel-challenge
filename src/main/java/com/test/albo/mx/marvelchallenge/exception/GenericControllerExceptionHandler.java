package com.test.albo.mx.marvelchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GenericControllerExceptionHandler {

	@ExceptionHandler(GenericNoContentException.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String colaboratorNoContent(GenericNoContentException exception) {
		return null;
	}

	@ExceptionHandler(GenericNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String colaboratorNotFound(GenericNotFoundException exception) {
		return null;
	}

	@ExceptionHandler(ExternalServiceException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public String externalServiceException(ExternalServiceException exception) {
		return "No es posible interpretar la api externa";
	}
}
