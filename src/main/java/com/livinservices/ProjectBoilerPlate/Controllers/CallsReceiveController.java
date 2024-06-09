package com.livinservices.ProjectBoilerPlate.Controllers;

import com.livinservices.ProjectBoilerPlate.Models.Call;
import com.livinservices.ProjectBoilerPlate.Models.SoftwareWebhook;
import com.livinservices.ProjectBoilerPlate.Models.User;
import com.livinservices.ProjectBoilerPlate.Services.CallService;
import com.livinservices.ProjectBoilerPlate.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class CallsReceiveController
{
	private final CallService callsService;
	private final UserService userService;
	CallsReceiveController(CallService callsService, UserService userService){

		this.callsService = callsService;
		this.userService = userService;
	}

	@RequestMapping("/dialer_endpoint")
	public String receiveCallDialer(){
		return "Call received";
	}

	@RequestMapping("/software_endpoint")
	public ResponseEntity<SoftwareWebhook> handleData( SoftwareWebhook data) {
		//get the user id from request
		Long userId = data.getUser();
		//get the user from the database
		Optional<User> user = userService.getUserById(userId);
		if(user.isEmpty()){
			return ResponseEntity.badRequest().build();
		}
		//check if call with same number exists
		List<Call> callByPhoneNumber = callsService.getCallsByPhoneNumber(data.getPhone());
		if(callByPhoneNumber.size() > 0){
			return ResponseEntity.ok(data);
		}

		//save the call
		Call call =  new Call();
		call.setPhoneNumber(data.getPhone());
		call.setMadeBy(user.get());
		call.setStatus( "Copied" );
		LocalDateTime now = LocalDateTime.now();
		call.setMadeAt(now);

		callsService.createCall(call);

		return ResponseEntity.ok(data);
	}

	public String receiveCall(){
		return "Call received";
	}
}
