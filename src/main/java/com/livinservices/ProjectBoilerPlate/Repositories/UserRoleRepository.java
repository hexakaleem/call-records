package com.livinservices.ProjectBoilerPlate.Repositories;

import com.livinservices.ProjectBoilerPlate.Models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>
{
}