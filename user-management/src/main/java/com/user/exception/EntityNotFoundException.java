package com.user.exception;

public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = -557027085665932575L;
	
	public EntityNotFoundException(String id) {
		super("Unable to find entity for: "+id);
	}
}
