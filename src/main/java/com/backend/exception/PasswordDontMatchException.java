package com.backend.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PasswordDontMatchException extends ResponseStatusException {
	
    private static final long serialVersionUID = 1L;

	public PasswordDontMatchException() {
        super(HttpStatus.BAD_REQUEST, "passwords don't match");
    }
}
