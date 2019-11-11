package com.ocheretyany.alexander.REST.exception;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotFoundException(long recordId) {
		super(String.format("Record with ID = '%s' is not found!", recordId));
	}
}
