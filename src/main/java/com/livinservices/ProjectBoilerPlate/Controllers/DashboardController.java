package com.livinservices.ProjectBoilerPlate.Controllers;



import com.livinservices.ProjectBoilerPlate.Forms.CreateOrganizationForm;
import com.livinservices.ProjectBoilerPlate.Models.*;
import com.livinservices.ProjectBoilerPlate.Services.CallService;
import com.livinservices.ProjectBoilerPlate.Services.OrganizationService;
import com.livinservices.ProjectBoilerPlate.Services.TeamService;
import com.livinservices.ProjectBoilerPlate.Services.UserService;
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
			//check user role
			List<Role> roles = authenticatedUser.get().getRoles();
			//check if admin
			boolean hasAdminRole = roles.stream().anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN"));
			if(hasAdminRole){
				return "redirect:/admin_section/dashboard";
			}
			//check if manager
			boolean hasManagerRole = roles.stream().anyMatch(role -> role.getName().equalsIgnoreCase("MANAGER"));
			if(hasManagerRole){
				return "redirect:/manager_section/dashboard";
			}
			//check if agent
			boolean hasAgentRole = roles.stream().anyMatch(role -> role.getName().equalsIgnoreCase("AGENT"));
			if(hasAgentRole){
				return "redirect:/agent_section/dashboard";
			}

		}else{
			return "redirect:/login";
		}

		// Return the name of the Thymeleaf template to render
		return "/dashboard/admin_dashbaord";
	}

}
