package com.livinservices.ProjectBoilerPlate.Services;

import com.livinservices.ProjectBoilerPlate.Models.Call;
import com.livinservices.ProjectBoilerPlate.Models.User;
import com.livinservices.ProjectBoilerPlate.Repositories.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CallService {

	@Autowired
	private CallRepository callRepository;
	public Call createCall(Call call) {
		// Validate call data (e.g., phone number)
		return callRepository.save(call);
	}

	public Call updateStatus(Long callId, String status){
		Call call = callRepository.findById(callId).orElse(null);
		if (call == null) {
			return null;
		}
		call.setStatus(status);
		return callRepository.save(call);

	}
	public List<Call> getTodayCalls(Long userId){
		//get timoe now
		LocalDateTime now = LocalDateTime.now();
		//get start of day
		LocalDateTime startOfDay = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
		//get end of day
		LocalDateTime endOfDay = now.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
		return callRepository.findByMadeBy_IdAndMadeAtBetween(userId, startOfDay, endOfDay);

	}
	public List<Call> getCurrentMonthCalls(Long userId){
		//get timoe now
		LocalDateTime now = LocalDateTime.now();
		//get start of month
		LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
		//get end of month
		LocalDateTime endOfMonth = now.withDayOfMonth(now.getMonth().length(now.toLocalDate().isLeapYear())).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
		return callRepository.findByMadeBy_IdAndMadeAtBetween(userId, startOfMonth, endOfMonth);
	}
	public List<Call> getCallsByUser(Long userId) {
		return callRepository.findByMadeBy_Id(userId);
	}

	public List<Call> getAllCalls(){
		return callRepository.findAll();
	}
}