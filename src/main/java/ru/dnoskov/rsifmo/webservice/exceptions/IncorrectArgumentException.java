package ru.dnoskov.rsifmo.webservice.exceptions;

public class IncorrectArgumentException extends Exception {

	private static final long serialVersionUID = -5678882112887569304L;

	private final static String BASE_MESSAGE = "Argument %s is incorrect!";

	public IncorrectArgumentException(String argument) {
		super(String.format(BASE_MESSAGE, argument));
	}
	
	public IncorrectArgumentException(String message, Throwable cause) {
		super(message, cause);
	}
}
