package com.livinservices.ProjectBoilerPlate.CustomExceptions;


public class OrganizationNotFoundException extends RuntimeException {
	public OrganizationNotFoundException(String message) {
		super(message);
	}
}