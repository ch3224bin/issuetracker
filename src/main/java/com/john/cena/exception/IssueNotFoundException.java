package com.john.cena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IssueNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1305801849273404977L;

	public IssueNotFoundException(String issueNo) {
		super("could not find issue '" + issueNo + "'.");
	}
}
