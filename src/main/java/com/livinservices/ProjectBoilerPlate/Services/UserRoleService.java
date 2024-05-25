package com.livinservices.ProjectBoilerPlate.Services;

import com.livinservices.ProjectBoilerPlate.Models.Role;
import com.livinservices.ProjectBoilerPlate.Models.User;
import com.livinservices.ProjectBoilerPlate.Models.UserRole;
import com.livinservices.ProjectBoilerPlate.Repositories.UserRepository;
import com.livinservices.ProjectBoilerPlate.Repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

	@Autowired
	private UserRoleRepository userRoleRepository;


	public UserRole assignRoleToUser(User user, Role role) {

		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);

		return userRoleRepository.save( userRole );
	}

	// Additional methods for user role management (optional)
}