package ru.dnoskov.rsifmo.webservice.exceptions;

public class WorkWithSQLException extends Exception {

	private static final long serialVersionUID = -1176041923687008928L;

	private final static String BASE_MESSAGE = "Error during work with SQL!";

	public WorkWithSQLException() {
		super(BASE_MESSAGE);
	}
	
	public WorkWithSQLException(String message, Throwable cause) {
		super(message, cause);
	}

}
