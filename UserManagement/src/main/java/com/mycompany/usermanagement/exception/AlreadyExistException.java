package com.mycompany.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = -8392669950937615577L;

	public AlreadyExistException(String exception)
	{
		super(exception);
	}
}
