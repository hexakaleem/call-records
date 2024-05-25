package com.livinservices.ProjectBoilerPlate.Repositories;

import com.livinservices.ProjectBoilerPlate.Models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long>
{
}