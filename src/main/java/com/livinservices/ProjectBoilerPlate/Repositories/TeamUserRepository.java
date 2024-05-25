package com.livinservices.ProjectBoilerPlate.Repositories;

import com.livinservices.ProjectBoilerPlate.Models.Call;
import com.livinservices.ProjectBoilerPlate.Models.Team;
import com.livinservices.ProjectBoilerPlate.Models.TeamUser;
import com.livinservices.ProjectBoilerPlate.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamUserRepository extends JpaRepository<TeamUser, Long>
{
	TeamUser findTeamUserByTeamAndMember(Team team, User agent);
}
