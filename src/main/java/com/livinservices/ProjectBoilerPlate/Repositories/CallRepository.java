package com.livinservices.ProjectBoilerPlate.Repositories;

import com.livinservices.ProjectBoilerPlate.Models.Call;
import com.livinservices.ProjectBoilerPlate.Models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;
@Repository
public interface CallRepository extends JpaRepository<Call, Long>
{
	List<Call> findByPhoneNumber(String phoneNumber);
	List<Call> findByMadeBy_Id(Long userId);
	List<Call> findByMadeBy_IdAndMadeAtBetween(Long userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
