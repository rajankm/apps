package com.user.exception;

public class BadCredentialsException extends Exception {
	
	private static final long serialVersionUID = -1595601808581082127L;

	public BadCredentialsException(String username) {
		super("User not found for: "+username);
	}
}
