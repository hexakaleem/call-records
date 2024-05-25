package com.livinservices.ProjectBoilerPlate.Repositories;

import com.livinservices.ProjectBoilerPlate.Models.Organization;
import com.livinservices.ProjectBoilerPlate.Models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>
{
	Team findTeamByNameAndOrganization(String name, Organization organization);
}