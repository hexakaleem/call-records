package com.livinservices.ProjectBoilerPlate.Models.Interfaces;

import com.livinservices.ProjectBoilerPlate.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);
}