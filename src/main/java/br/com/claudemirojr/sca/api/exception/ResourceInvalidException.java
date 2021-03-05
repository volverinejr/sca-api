package br.com.claudemirojr.sca.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceInvalidException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ResourceInvalidException(String exception) {
		super(exception);
	}
	
}
