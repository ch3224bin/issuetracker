package com.john.cena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 8787310824602069305L;

	public CommentNotFoundException(String commentNo) {
		super("could not find comment '" + commentNo + "'.");
	}
}
