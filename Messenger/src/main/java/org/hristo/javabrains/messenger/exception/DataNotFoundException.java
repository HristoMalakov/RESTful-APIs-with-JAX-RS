package org.hristo.javabrains.messenger.exception;

public class DataNotFoundException extends RuntimeException{

	private static final long serialVersionID = 12345678;
	
	public DataNotFoundException(String message) {
		super(message);
	}
	
}
