package br.com.claudemirojr.sca.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ResourceNaoPermitidoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ResourceNaoPermitidoException(String exception) {
		super(exception);
	}
	
}
