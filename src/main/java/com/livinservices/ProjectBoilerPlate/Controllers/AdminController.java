package com.livinservices.ProjectBoilerPlate.Controllers;

import com.livinservices.ProjectBoilerPlate.CustomExceptions.TeamNotFoundException;
import com.livinservices.ProjectBoilerPlate.Extras.RegisterRequest;
import com.livinservices.ProjectBoilerPlate.Forms.CreateOrganizationForm;
import com.livinservices.ProjectBoilerPlate.Forms.CreateRoleForm;
import com.livinservices.ProjectBoilerPlate.Models.Organization;
import com.livinservices.ProjectBoilerPlate.Models.Role;
import com.livinservices.ProjectBoilerPlate.Models.Team;
import com.livinservices.ProjectBoilerPlate.Models.User;
import com.livinservices.ProjectBoilerPlate.Services.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin_section")
public class AdminController
{
	private final UserService userService;
	private final OrganizationService organizationService;
	private final TeamService teamService;
	private final RoleService roleService;
	private final  UserRoleService userRoleService;
	private final  TeamUserService teamUserService;
	public AdminController(UserService userService, OrganizationService organizationService, TeamService teamService, RoleService roleService, UserRoleService userRoleService, TeamUserService teamUserService) {
		this.userService = userService;
		this.organizationService = organizationService;
		this.teamService = teamService;
		this.roleService = roleService;
		this.userRoleService = userRoleService;
		this.teamUserService = teamUserService;
	}
	@PostMapping("/createOrganization")
	public String createOrganization(@Valid @ModelAttribute("organization")CreateOrganizationForm request, Principal principal, Model model, BindingResult result) {
		System.out.println("Called");
		if (principal != null) {
			Optional<User> authenticatedUser = userService.findUserByEmail(principal.getName());
			List<Role> roles = authenticatedUser.get().getRoles();
			boolean hasAdminRole = roles.stream().anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN"));

			if (!hasAdminRole){
				model.addAttribute( "error" , "You dont have access to do this operation" );
				return "redirect:/dashboard";}
			else{
				// Create organization
				// Create a new Organization and Team
				Organization organization = new Organization();
				organization.setName(request.getOrgName());
				organization.setCreated_by(authenticatedUser.get());
				Organization savedOrganization = organizationService.createOrganization(organization);


				//create manager
				User user = new User();
				user.setName(request.getManName());
				user.setUserName(request.getManUserName());
				user.setEmail(request.getManEmail());
				user.setPassword(request.getManPassword());
				user.setOrganization(savedOrganization);
				user.setCreatedBy( authenticatedUser.get());
				userService.save(user);
				//assgin user the role of manager
				// Assign a default role to the user
				Role role = roleService.findByName("MANAGER");
				if (role == null) {
					role = new Role();
					role.setName("MANAGER");
					roleService.create(role);
				}

				userRoleService.assignRoleToUser(   user, role);

				//create Team in OrganizaZtion
				Team team = new Team();
				team.setName("Default Team");
				team.setOrganization(savedOrganization);
				try{
					teamService.createTeam(team);
				}catch(TeamNotFoundException e){
					//handle exception

				}


				teamUserService.addUserToTeam(  user.getId(), team.getId());
				return "redirect:/organizations?success";
			}
		}else{
			return "redirect:/login";
		}
	}
}
