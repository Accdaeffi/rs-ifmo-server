package ru.dnoskov.rsifmo.webservice.services.throttling;

public class ThrottlingException extends Exception {

	private static final long serialVersionUID = 92386251771107666L;

	private final static String BASE_MESSAGE = "Throttling exception!";

	public ThrottlingException() {
		super(BASE_MESSAGE);
	}
	
	public ThrottlingException(String message, Throwable cause) {
		super(message, cause);
	}

}
