package br.com.riachuelo.api.starwars.services.exceptions;

public class ServiceUnavailable extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceUnavailable(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceUnavailable(String message) {
		super(message);
	}

}
