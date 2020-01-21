package com.user.exception;

public class EntityPersistanceException extends Exception{

	private static final long serialVersionUID = 9121632370413670664L;
	
	public EntityPersistanceException(String msg) {
		super("Unable to perssist entity for:"+ msg);
	}
}
