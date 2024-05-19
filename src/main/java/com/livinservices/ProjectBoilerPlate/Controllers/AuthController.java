package com.livinservices.ProjectBoilerPlate.Controllers;

import com.livinservices.ProjectBoilerPlate.Models.Interfaces.UserService;
import com.livinservices.ProjectBoilerPlate.Models.Extras.RegisterRequest;
import com.livinservices.ProjectBoilerPlate.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AuthController {
	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
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

		User existingUser = userService.findUserByEmail(registerRequest.getEmail());


		if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
			result.rejectValue("email", null, "There is already an account registered with the same email");
		}


		//check if username already exist
		User existingUserName = userService.findUserByUserName(registerRequest.getUserName());
		if (existingUserName != null && existingUserName.getUserName() != null && !existingUserName.getUserName().isEmpty()) {
			result.rejectValue("userName", null, "Username already exist");
		}

		if (result.hasErrors()) {
			model.addAttribute("user", registerRequest);
			return "register";
		}
		userService.saveUser(registerRequest);
		return "redirect:/register?success";
	}

	@GetMapping("/login")
	public String login(Principal principal) {

		if (principal != null) {
			System.out.println("Called");
			return "redirect:/dashboard";
		}
		return "login";
	}
}
