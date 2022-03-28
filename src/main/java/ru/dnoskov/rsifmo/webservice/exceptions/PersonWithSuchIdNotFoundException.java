package ru.dnoskov.rsifmo.webservice.exceptions;

public class PersonWithSuchIdNotFoundException extends Exception {

	private static final long serialVersionUID = 1585787107936011181L;

	private final static String BASE_MESSAGE = "Can't found person with id %d!";

	public PersonWithSuchIdNotFoundException(int id) {
		super(String.format(BASE_MESSAGE, id));
	}
	
	public PersonWithSuchIdNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
