package com.livinservices.ProjectBoilerPlate.Services;

import com.livinservices.ProjectBoilerPlate.CustomExceptions.OrganizationNotFoundException;
import com.livinservices.ProjectBoilerPlate.Models.Organization;
import com.livinservices.ProjectBoilerPlate.Models.Role;
import com.livinservices.ProjectBoilerPlate.Repositories.OrganizationRepository;
import com.livinservices.ProjectBoilerPlate.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public Role create(Role role) {
		// Validate role data (e.g., name)
		//check if role already exist
		Role role1 = roleRepository.findByName(role.getName());
		if(role1 != null){
			return role1;
		}

		return roleRepository.save(role);
	}

	public Role findByName(String manager)
	{
		return roleRepository.findByName(manager);
	}
}
