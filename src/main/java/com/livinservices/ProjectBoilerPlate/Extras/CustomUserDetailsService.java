package com.livinservices.ProjectBoilerPlate.Extras;


import com.livinservices.ProjectBoilerPlate.Models.Role;
import com.livinservices.ProjectBoilerPlate.Models.User;
import com.livinservices.ProjectBoilerPlate.Repositories.UserRepository;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);

		if (user.isPresent()) {
			return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
					user.get().getPassword(),
					mapRolesToAuthorities(user.get().getRoles()));
		}else{
			throw new UsernameNotFoundException("Invalid username or password.");
		}
	}

	private Collection < ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
	}
}