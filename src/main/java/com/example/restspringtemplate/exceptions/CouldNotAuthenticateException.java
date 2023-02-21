package com.example.restspringtemplate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CouldNotAuthenticateException extends RuntimeException {
	public CouldNotAuthenticateException(String message) {
		super(message);
	}
}
