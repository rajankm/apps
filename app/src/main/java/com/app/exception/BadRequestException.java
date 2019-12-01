package com.app.exception;

public class BadRequestException extends Exception{

	private static final long serialVersionUID = -1345284660200530630L;
	
	public BadRequestException(String message) {
		super(message);
	}
}
