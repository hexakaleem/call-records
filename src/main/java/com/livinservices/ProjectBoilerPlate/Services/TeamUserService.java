package com.livinservices.ProjectBoilerPlate.Services;

import com.livinservices.ProjectBoilerPlate.CustomExceptions.TeamUserException;
import com.livinservices.ProjectBoilerPlate.Models.*;
import com.livinservices.ProjectBoilerPlate.Models.TeamUser;
import com.livinservices.ProjectBoilerPlate.Models.User;
import com.livinservices.ProjectBoilerPlate.Repositories.TeamRepository;
import com.livinservices.ProjectBoilerPlate.Repositories.TeamUserRepository;
import com.livinservices.ProjectBoilerPlate.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamUserService {

	@Autowired
	private TeamUserRepository teamUserRepository;

	@Autowired
	private UserRepository userRepository;  // Added for user existence check

	@Autowired
	private TeamRepository teamRepository;

	public void addUserToTeam( Long userId, Long teamId) throws TeamUserException
	{

		Team team = teamRepository.findById(teamId).orElseThrow(() ->
				new TeamUserException("Team with ID " + teamId + " not found"));
		User user = userRepository.findById(userId).orElseThrow(() ->
				new TeamUserException("Agent with ID " + userId + " not found"));

		// Check if agent is already a member (optional)
		TeamUser agentAlreadyInTeam = teamUserRepository.findTeamUserByTeamAndMember(team, user);
		if (agentAlreadyInTeam !=  null)  {
			throw new TeamUserException("Agent with ID " + user.getId() + " is already a member of this team");
		}

		// Create and save the TeamUser association
		TeamUser teamUser = new TeamUser();
		teamUser.setTeam(team);
		teamUser.setMember(user);
		teamUserRepository.save(teamUser);
	}

	public void removeAgentFromTeam(Long managerId, Long agentId, Long teamId) throws TeamUserException {
		// Fetch required entities
		User manager = userRepository.findById(managerId).orElseThrow(() ->
				new TeamUserException("Manager with ID " + managerId + " not found"));
		Team team = teamRepository.findById(teamId).orElseThrow(() ->
				new TeamUserException("Team with ID " + teamId + " not found"));
		User agent = userRepository.findById(agentId).orElseThrow(() ->
				new TeamUserException("Agent with ID " + agentId + " not found"));

		// Verify manager has permission (e.g., created the team or has admin role)
		if (!hasPermissionToAddAgent(manager, team)) {
			throw new TeamUserException("Manager does not have permission to add agents to this team");
		}

		// Check if agent is actually a member of the team
		TeamUser    teamUser = teamUserRepository.findTeamUserByTeamAndMember(team, agent);
		if (teamUser == null) {
			throw new TeamUserException("Agent with ID " + agentId + " is not a member of this team");
		}

		// Remove the association
		teamUserRepository.delete(teamUser);
	}

	// Helper method for permission check (implementation will depend on your logic)
	private boolean hasPermissionToAddAgent(User manager, Team team) {
		List<Role> roles = manager.getRoles();
		//check if role with manager exists
		if(roles.isEmpty())
		{
			return false;
		}
		//check if role with manager is manager
		if(!roles.getFirst().getName().equals("Manager"))
		{
			return false;
		}
		TeamUser teamUser = teamUserRepository.findTeamUserByTeamAndMember(team, manager);
		if (teamUser == null) {
			return false;
		}
		 return true;
	}
}

// Custom exception class for TeamUserService errors
