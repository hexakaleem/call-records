package com.livinservices.ProjectBoilerPlate.Controllers;

import com.livinservices.ProjectBoilerPlate.Extras.RegisterRequest;
import com.livinservices.ProjectBoilerPlate.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.Principal;

@Controller
public class AdminController
{
	private final UserService userService;
	public AdminController(UserService userService) {
		this.userService = userService;
	}
	@PostMapping("/createRole")
	public String creatRole( RegisterRequest registerRequest, Principal principal) {
		if (principal != null) {
			return "redirect:/dashboard";
		}
		RegisterRequest user = new RegisterRequest();

		return "register";
	}
}
