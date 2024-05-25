package com.livinservices.ProjectBoilerPlate.CustomExceptions;

public class TeamUserException extends RuntimeException {
	public TeamUserException(String message) {
		super(message);
	}
}