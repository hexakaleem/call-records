package com.livinservices.ProjectBoilerPlate.Services;

import com.livinservices.ProjectBoilerPlate.CustomExceptions.TeamNotFoundException;
import com.livinservices.ProjectBoilerPlate.Models.Organization;
import com.livinservices.ProjectBoilerPlate.Models.Team;
import com.livinservices.ProjectBoilerPlate.Repositories.OrganizationRepository;
import com.livinservices.ProjectBoilerPlate.Repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private OrganizationRepository organizationRepository;

	public Team createTeam(Team team) {
		// Validate team data (e.g., name)
		//check if team with same name exist in organizzation
		Team teamWithSameName = teamRepository.findTeamByNameAndOrganization(team.getName(), team.getOrganization());
		if (teamWithSameName != null) {
			throw new TeamNotFoundException("Team with name " + team.getName() + " already exists in the organization");
		}

		return teamRepository.save(team);
	}

	public Team getTeamById(Long teamId) {
		return teamRepository.findById(teamId).orElseThrow(() ->
				new TeamNotFoundException("Team with ID " + teamId + " not found"));
	}

	public List<Team> getAllTeams() {
		return teamRepository.findAll();
	}


	//findTeamByNameAndOrganization
	public Team findTeamByNameAndOrganization(String teamName, Organization organization) {
		return teamRepository.findTeamByNameAndOrganization(teamName, organization);
	}

	// Other methods for team management (e.g., update team)
}

// Custom exception class for team-related errors
