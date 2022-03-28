package ru.dnoskov.rsifmo.webservice.exceptions;

public class EmptyArgumentException extends Exception {

	private static final long serialVersionUID = -7382691528512756973L;

	private final static String BASE_MESSAGE = "Argument %s is empty!";
	
	String message;
	
	public EmptyArgumentException(String argument) {
		super(String.format(BASE_MESSAGE, argument));
	}
	
	public EmptyArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

}
