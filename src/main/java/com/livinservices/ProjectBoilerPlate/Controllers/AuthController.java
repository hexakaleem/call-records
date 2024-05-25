package com.livinservices.ProjectBoilerPlate.Controllers;

import com.livinservices.ProjectBoilerPlate.CustomExceptions.UserNotFoundException;
import com.livinservices.ProjectBoilerPlate.Models.*;
import com.livinservices.ProjectBoilerPlate.Services.*;
import com.livinservices.ProjectBoilerPlate.Extras.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@Controller
public class AuthController {
	private final UserService userService;
	private final RoleService roleService;
	private final OrganizationService organizationService;
	private final TeamService teamService;
	private final TeamUserService teamUserService;

	@Autowired
	public AuthController(UserService userService, RoleService roleService,
	                      OrganizationService organizationService, TeamService teamService, TeamUserService teamUserService) {
		this.userService = userService;
		this.roleService = roleService;
		this.organizationService = organizationService;
		this.teamService = teamService;
		this.teamUserService = teamUserService;
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model, Principal principal) {
		if (principal != null) {
			return "redirect:/dashboard";
		}
		RegisterRequest user = new RegisterRequest();
		model.addAttribute("user", user);
		return "register";
	}

	@PostMapping("/register")
	public String registration(@Valid @ModelAttribute("user") RegisterRequest registerRequest,
	                           BindingResult result,
	                           Model model) {

		if(registerRequest.getEmail().isEmpty()){
			result.rejectValue("email", null, "Email is required");
		}
		if(registerRequest.getPassword().isEmpty()){
			result.rejectValue("password", null, "Password is required");
		}
		if(registerRequest.getFirstName().isEmpty()){
			result.rejectValue("firstName", null, "First name is required");
		}
		if(registerRequest.getOrganization().isEmpty()){
			result.rejectValue("organization", null, "Organization is required");
		}
		if(registerRequest.getUserName().isEmpty()){
			result.rejectValue("userName", null, "Username is required");
		}
		Optional<User> existingUser = userService.findUserByEmail(registerRequest.getEmail());
		//create and save user
		if (existingUser.isPresent() && existingUser.get().getEmail() != null && !existingUser.get().getEmail().isEmpty()) {
			result.rejectValue("email", null, "There is already an account registered with the same email");
		}
		//check if username already exist
		Optional<User> existingUserName = userService.findUserByUserName(registerRequest.getUserName());
		if (existingUserName.isPresent() && existingUserName.get().getUserName() != null && !existingUserName.get().getUserName().isEmpty()) {
			result.rejectValue("userName", null, "Username already exist");
		}

		if (result.hasErrors()) {
			model.addAttribute("user", registerRequest);
			return "register";
		}

		// Create a new Organization and Team
		Organization organization = new Organization();
		organization.setName(registerRequest.getOrganization());
		Organization savedOrganization = organizationService.createOrganization(organization);

		Team team = new Team();
		team.setName("Default Team");
		team.setOrganization(savedOrganization);
		Team savedTeam = teamService.createTeam(team);

		// Assign a default role to the user
		Role role = roleService.findByName("MANAGER");
		if (role == null) {
			role = new Role();
			role.setName("MANAGER");
			roleService.create(role);
		}

		// Create a new User
		User user = userService.registerUser(registerRequest, organization,role);
		organization.setCreated_by( user );
		organizationService.createOrganization(organization);

		teamUserService.addUserToTeam(  user.getId(), savedTeam.getId());
		return "redirect:/register?success";
	}

	@GetMapping("/login")
	public String login(Principal principal) {

		if (principal != null) {
			return "redirect:/dashboard";
		}
		return "login";
	}
}


