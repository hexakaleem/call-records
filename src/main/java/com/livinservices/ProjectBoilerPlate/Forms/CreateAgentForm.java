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
public class CreateAgentForm
{
	@NotEmpty(message = "Please Enter Agent Name")
	private String agentName;


	@NotEmpty(message = "Agent team cannot be empty.")
	private Long team;

	@NotEmpty(message = "Agent userName cannot be empty.")
	private String agentUserName;

	@NotEmpty(message = "agent email cannot be empty.")
	@Email(message = "Invalid email format")
	private String agentEmail;

	@NotEmpty(message = "PLease enter Temporary Password for Agent")
	private String agentPassword;
}
