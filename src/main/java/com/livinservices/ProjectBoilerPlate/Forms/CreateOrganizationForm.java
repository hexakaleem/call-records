package com.livinservices.ProjectBoilerPlate.Forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganizationForm
{
	@NotEmpty(message = "Please Enter Organization Name")
	private String orgName;

	@NotEmpty(message = "Manager Name cannot be empty.")
	private String manName;

	@NotEmpty(message = "Manager userName cannot be empty.")
	private String manUserName;

	@NotEmpty(message = "Manager email cannot be empty.")
	@Email(message = "Invalid email format")
	private String manEmail;

	@NotEmpty(message = "PLease enter Temporary Password for Manager")
	private String manPassword;

}
