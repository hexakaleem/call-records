package com.livinservices.ProjectBoilerPlate.CustomExceptions;


public class UserRegistrationException extends RuntimeException {
	public UserRegistrationException(String message) {
		super(message);
	}
}