package com.livinservices.ProjectBoilerPlate.Controllers;



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

	public DashboardController(UserService userService)
	{
		this.userService = userService;
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model, Principal principal) {
		if (principal != null) {
			// Retrieve authenticated user based on principal's email
			Optional<User> authenticatedUser = userService.findUserByEmail(principal.getName());

			// Example: Fetch user's teams and agents (adjust as per your application's logic)
			List<Team> userTeams = authenticatedUser.get().getTeams();

			// Add data to the model
			model.addAttribute("user", authenticatedUser);
			model.addAttribute("userTeams", userTeams);

		}else{
			return "redirect:/login";
		}

		// Return the name of the Thymeleaf template to render
		return "dashboard/dashboard";
	}

}
