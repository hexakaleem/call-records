package com.livinservices.ProjectBoilerPlate.Extras;


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
public class RegisterRequest
{
	private Long id;

	@NotEmpty(message = "first name is necessary")
	private String firstName;

	@NotEmpty(message = "Organization is necessary")
	private String organization;

	@NotEmpty(message = "Username should not be empty")
	private String userName;

	@NotEmpty(message = "Email should not be empty")
	@Email(message = "Invalid email format")
	private String email;

	@NotEmpty(message = "Password should not be empty")
	private String password;

	@NotEmpty(message = "Role should not be empty")
	private String role;

}
