package com.livinservices.ProjectBoilerPlate.CustomExceptions;

public class TeamNotFoundException extends RuntimeException {
	public TeamNotFoundException(String message) {
		super(message);
	}
}