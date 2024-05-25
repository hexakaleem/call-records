package com.livinservices.ProjectBoilerPlate.Services;


import com.livinservices.ProjectBoilerPlate.CustomExceptions.UserNotFoundException;
import com.livinservices.ProjectBoilerPlate.CustomExceptions.UserRegistrationException;
import com.livinservices.ProjectBoilerPlate.Models.*;
import com.livinservices.ProjectBoilerPlate.Models.User;
import com.livinservices.ProjectBoilerPlate.Extras.RegisterRequest;
import com.livinservices.ProjectBoilerPlate.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;



@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleService userRoleService;  // Optional, for assigning roles

	@Autowired
	private PasswordEncoder bcryptEncoder;

	public User registerUser(RegisterRequest userData, Organization organization, Role role) throws UserRegistrationException
	{
		// Validate user data (e.g., email, password)
		User user = new User();
		user.setName( userData.getFirstName() );
		user.setUserName(userData.getUserName());
		user.setEmail(userData.getEmail());
		user.setOrganization( organization );
		user.setCreatedBy( user );
		user.setPassword(bcryptEncoder.encode(userData.getPassword())); // Assuming password hashing


		user = userRepository.save(user);
	  	userRoleService.assignRoleToUser( user, role );  // Assign role to user
		return user;
	}

	public Optional<User> getUserById(Long userId) {
		return userRepository.findById(userId);
	}
	public Optional<User> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<User> findUserByUserName(String userName){
		return userRepository.findByUserName(userName);

	}
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	// Additional methods for user management (e.g., delete user, search users)
}

// Custom exception classes for user-related errors



