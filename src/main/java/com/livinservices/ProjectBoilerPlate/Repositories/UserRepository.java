package com.livinservices.ProjectBoilerPlate.Repositories;

import com.livinservices.ProjectBoilerPlate.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUserName(String username);

	Optional<User> findByEmail(String email);
}