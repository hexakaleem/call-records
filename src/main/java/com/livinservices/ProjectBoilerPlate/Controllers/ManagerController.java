package com.livinservices.ProjectBoilerPlate.Controllers;

import com.livinservices.ProjectBoilerPlate.Extras.RegisterRequest;
import com.livinservices.ProjectBoilerPlate.Forms.AddMemberToTeamForm;
import com.livinservices.ProjectBoilerPlate.Forms.CreateAgentForm;
import com.livinservices.ProjectBoilerPlate.Forms.CreateOrganizationForm;
import com.livinservices.ProjectBoilerPlate.Forms.CreateTeamForm;
import com.livinservices.ProjectBoilerPlate.Models.*;
import com.livinservices.ProjectBoilerPlate.Services.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manager_section")
public class ManagerController
{
private final UserService userService;
private final OrganizationService organizationService;
private final TeamService teamService;
private final CallService callsService;
	private final  UserRoleService userRoleService;
	private final  TeamUserService teamUserService;
	private final  RoleService roleService;

	ManagerController(UserService userService, OrganizationService organizationService, TeamService teamService, CallService callsService, UserRoleService userRoleService, TeamUserService teamUserService, RoleService roleService){
		this.userService = userService;
		this.organizationService = organizationService;
		this.teamService = teamService;
		this.callsService = callsService;
		this.userRoleService = userRoleService;
		this.teamUserService = teamUserService;
		this.roleService = roleService;
	}

	@PostMapping("/create_team")
	public String createTeam(@Valid @ModelAttribute("createTeamForm") CreateTeamForm teamForm,
	                         BindingResult result, Model model, Principal principal){
		if (principal != null){
			// Retrieve authenticated user based on principal's email
			Optional<User> authenticatedUser = userService.findUserByEmail(principal.getName());

			Organization org= 	authenticatedUser.get().getOrganization();

			//check if team with the name is already in  this organziation
			Team team = teamService.findTeamByNameAndOrganization(teamForm.getTeamName(), org);
			if (team != null){
				result.rejectValue("teamName", null, "Team with the name already exists in the organization");
			}
			Team t = new Team();
			t.setName(teamForm.getTeamName());
			t.setOrganization(org);

			teamService.createTeam( t);
			if(result.hasErrors()){
				model.addAttribute("createTeamForm", teamForm);
				return "manager_panel/dashboard";

			}

		}else{
			return "redirect:/login";
		}
		return "redirect:/dashboard";
	}

	@PostMapping("/create_agent")
	public String creatAgent(@Valid @ModelAttribute("createAgentForm") CreateAgentForm agentForm,
	                         BindingResult result, Model model, Principal principal){
		if (principal != null){
			// Retrieve authenticated user based on principal's email
			Optional<User> authenticatedUser = userService.findUserByEmail(principal.getName());
			Organization org= 	authenticatedUser.get().getOrganization();

			//cgeck if user already exists
			Optional<User> user = userService.findUserByEmail(agentForm.getAgentEmail());
			if (user.isPresent()){
				result.rejectValue("email", null, "User with the email already exists");
			}
			//check user name
			Optional<User> userName = userService.findUserByUserName(agentForm.getAgentUserName());
			if (userName.isPresent()){
				result.rejectValue("userName", null, "User with the username already exists");
			}
			if(result.hasErrors()){
				model.addAttribute("createAgentForm", agentForm);
				return "redirect:/manager_panel/dashboard";
			}
			RegisterRequest data = new RegisterRequest();
			data.setEmail(agentForm.getAgentEmail());
			data.setFirstName(agentForm.getAgentName());
			data.setOrganization(org.getName());
			data.setPassword(agentForm.getAgentPassword());
			data.setUserName(agentForm.getAgentUserName());

			Role role = roleService.findByName("AGENT");
			if (role == null) {
				role = new Role();
				role.setName("AGENT");
				roleService.create(role);
			}
			User u = userService.registerUser( data, org, role, authenticatedUser.get());

			teamUserService.addUserToTeam( u.getId(), agentForm.getTeam());

		}else{
			return "redirect:/login";
		}
		return "redirect:/dashboard";
	}


	@GetMapping("/dashboard")
	public String dashboard(Model model, Principal principal){
		if (principal != null)
		{
			Optional<User> authenticatedUser = userService.findUserByEmail( principal.getName() );
			List<Team> userTeams = authenticatedUser.get().getTeams();
			Organization org =  authenticatedUser.get().getOrganization();
			List<User> orgUsers =  org.getUsers();
			//get all calls of orgUsers and make a combined list of all
			List<Call> orgCalls = orgUsers.stream().map(User::getCalls).flatMap(List::stream).toList();



			// Add data to the model
			model.addAttribute( "user", authenticatedUser.get() );
			model.addAttribute( "userTeams", org.getTeams() );
			model.addAttribute( "orgUsers", orgUsers );
			model.addAttribute( "orgCalls", orgCalls );
			model.addAttribute( "organization", org );

			CreateAgentForm createAgentForm = new CreateAgentForm();
			AddMemberToTeamForm addMemberToTeamForm = new AddMemberToTeamForm();
			CreateTeamForm creatTeamForm = new CreateTeamForm();

			model.addAttribute( "createAgentForm", createAgentForm );
			model.addAttribute( "addMemberToTeamForm", addMemberToTeamForm );
			model.addAttribute( "createTeamForm", creatTeamForm );

		}else{
			return "redirect:/login";
		}
		// Return the name of the Thymeleaf template to render
		return "/dashboard/manager_dashbaord";
	}
}
