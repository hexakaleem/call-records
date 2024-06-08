package com.livinservices.ProjectBoilerPlate.Controllers;



import com.livinservices.ProjectBoilerPlate.Forms.CreateOrganizationForm;
import com.livinservices.ProjectBoilerPlate.Models.Call;
import com.livinservices.ProjectBoilerPlate.Models.Organization;
import com.livinservices.ProjectBoilerPlate.Services.CallService;
import com.livinservices.ProjectBoilerPlate.Services.OrganizationService;
import com.livinservices.ProjectBoilerPlate.Services.TeamService;
import com.livinservices.ProjectBoilerPlate.Services.UserService;
import com.livinservices.ProjectBoilerPlate.Models.Team;
import com.livinservices.ProjectBoilerPlate.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class DashboardController
{
	private final UserService userService;
	private final OrganizationService organizationService;
	private final TeamService teamService;
	private final CallService callsService;

	public DashboardController(UserService userService, OrganizationService organizationService, TeamService teamService, CallService callsService)
	{
		this.userService = userService;
		this.organizationService = organizationService;
		this.teamService = teamService;
		this.callsService = callsService;
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model, Principal principal) {
		if (principal != null) {
			// Retrieve authenticated user based on principal's email
			Optional<User> authenticatedUser = userService.findUserByEmail(principal.getName());

			// Example: Fetch user's teams and agents (adjust as per your application's logic)
			List<Team> userTeams = authenticatedUser.get().getTeams();
			List<Organization> allOrganizations = organizationService.getAllOrganizations();
			List<Team> allTeams = teamService.getAllTeams();
			List<Call> allCalls= callsService.getAllCalls();
			List<User> allUsers = userService.getAllUsers();

			// Add data to the model
			model.addAttribute("user", authenticatedUser.get());
			model.addAttribute("userTeams", userTeams);
			model.addAttribute("allCalls", allCalls);
			model.addAttribute("allOrg", allOrganizations);
			model.addAttribute("allTeams", allTeams);
			model.addAttribute("allUsers", allUsers);

			CreateOrganizationForm createOrganizationForm = new CreateOrganizationForm();
			model.addAttribute("createOrganizationForm", createOrganizationForm);

		}else{
			return "redirect:/login";
		}

		// Return the name of the Thymeleaf template to render
		return "dashboard/dashboard";
	}

}
