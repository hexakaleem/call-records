package com.livinservices.ProjectBoilerPlate.Models.Interfaces;


import com.livinservices.ProjectBoilerPlate.Models.User;
import com.livinservices.ProjectBoilerPlate.Models.Extras.RegisterRequest;



import java.util.List;

public interface UserService {
	void saveUser(RegisterRequest registerRequest);

	User findUserByEmail(String email);
	User findUserByUserName(String email);

	List<RegisterRequest> findAllUsers();
}