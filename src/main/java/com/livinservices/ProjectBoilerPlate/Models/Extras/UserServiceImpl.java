package com.livinservices.ProjectBoilerPlate.Models.Extras;

import com.livinservices.ProjectBoilerPlate.Models.Interfaces.RoleRepository;
import com.livinservices.ProjectBoilerPlate.Models.Interfaces.UserRepository;
import com.livinservices.ProjectBoilerPlate.Models.Interfaces.UserService;
import com.livinservices.ProjectBoilerPlate.Models.Role;
import com.livinservices.ProjectBoilerPlate.Models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository,
	                       RoleRepository roleRepository,
	                       PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void saveUser(RegisterRequest registerRequest) {
		User user = new User();
		user.setName( registerRequest.getFirstName());
		user.setUserName( registerRequest.getUserName());

		user.setEmail( registerRequest.getEmail());
		// encrypt the password using spring security
		user.setPassword(passwordEncoder.encode( registerRequest.getPassword()));

		// check if role exists
		Role role = roleRepository.findByName("ROLE_ADMIN");
		if(role == null){
			role = createRole("ROLE_ADMIN");
		}
		user.setRoles( List.of( role ) );
		userRepository.save(user);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	public User findUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public List<RegisterRequest> findAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream()
				.map((user) -> mapToUserDto(user))
				.collect(Collectors.toList());
	}

	private RegisterRequest mapToUserDto(User user){
		RegisterRequest registerRequest = new RegisterRequest();
		String[] str = user.getName().split(" ");
		registerRequest.setFirstName(str[0]);
		registerRequest.setEmail(user.getEmail());
		return registerRequest;
	}

	private Role createRole(String roleName){
		Role role = new Role();
		role.setName(roleName);
		return roleRepository.save(role);
	}
}