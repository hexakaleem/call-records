package com.livinservices.ProjectBoilerPlate.Services;

import com.livinservices.ProjectBoilerPlate.CustomExceptions.OrganizationNotFoundException;
import com.livinservices.ProjectBoilerPlate.Extras.RegisterRequest;
import com.livinservices.ProjectBoilerPlate.Models.Organization;
import com.livinservices.ProjectBoilerPlate.Repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;

	public Organization createOrganization(Organization organization) {
		// Validate organization data (e.g., name)
		return organizationRepository.save(organization);
	}

	public Organization getOrganizationById(Long organizationId) {
		return organizationRepository.findById(organizationId).orElseThrow(() ->
				new OrganizationNotFoundException("Organization with ID " + organizationId + " not found"));
	}

	public List<Organization> getAllOrganizations() {
		return organizationRepository.findAll();
	}

	// Other methods for organization management (e.g., update organization)
}

