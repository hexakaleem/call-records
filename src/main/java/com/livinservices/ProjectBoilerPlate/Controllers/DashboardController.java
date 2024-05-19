package com.livinservices.ProjectBoilerPlate.Controllers;



import com.livinservices.ProjectBoilerPlate.Models.Interfaces.UserService;
import com.livinservices.ProjectBoilerPlate.Models.Team;
import com.livinservices.ProjectBoilerPlate.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class DashboardController
{
	private final UserService userService;

	public DashboardController(UserService userService)
	{
		this.userService = userService;
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model, Principal principal) {
		if (principal != null) {
			// Retrieve authenticated user based on principal's email
			User authenticatedUser = userService.findUserByEmail(principal.getName());

			// Example: Fetch user's teams and agents (adjust as per your application's logic)
			List<Team> userTeams = authenticatedUser.getTeams();

			// Add data to the model
			model.addAttribute("user", authenticatedUser);
			model.addAttribute("teams", userTeams);
		}else{
			return "redirect:/login";
		}

		// Return the name of the Thymeleaf template to render
		return "dashboard/dashboard";
	}

}
