package com.equifarmotp.controller;

import com.equifarmotp.dto.OtpValidationRequest;
import com.equifarmotp.entity.OtpEntity;
import com.equifarmotp.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equifarmotp.dto.OtpRequest;
import com.equifarmotp.dto.OtpResponseDto;
import com.equifarmotp.service.SmsService;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/otp")
@Slf4j
public class OtpController {

	@Autowired
	private SmsService smsService;
	@Autowired
	private OtpService otpService;
	
	@GetMapping("/process")
	public String processSMS() {
		return "SMS sent";
	}

//	@PostMapping("/send-otp")
//	public OtpResponseDto sendOtp(@RequestBody OtpRequest otpRequest) {
//		log.info("inside sendOtp :: "+otpRequest.getUsername());
//		return smsService.sendSMS(otpRequest);
//
//
//	}
//	@PostMapping("/add")
//	public ResponseEntity<OtpEntity> saveOtp(@RequestBody OtpEntity otpEntity){
//		return new ResponseEntity<OtpEntity>(otpService.saveOtp(otpEntity), HttpStatus.CREATED);
//	}
	@PostMapping("/validate-otp")
    public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
		log.info("inside validateOtp :: "+otpValidationRequest.getUsername()+" "+otpValidationRequest.getOtpNumber());
		return smsService.validateOtp(otpValidationRequest);
    }

	@PostMapping("/send-and-save-otp")
	public ResponseEntity<?> sendAndSaveOtp(@RequestBody OtpRequest otpRequest, OtpEntity otpEntity) {
		log.info("Inside sendAndSaveOtp :: {}", otpRequest.getUsername());

		// Send OTP

		OtpResponseDto otpResponseDto = smsService.sendSMS(otpRequest);
		System.out.println("response... "+otpResponseDto);
		// Save OTP to the database
//		OtpEntity otpEntity = new OtpEntity();

		otpEntity.setUsername(otpRequest.getUsername());
		otpEntity.setOtpNumber(otpResponseDto.getOtpNumber());
		otpEntity.setPhoneNumber(otpRequest.getPhoneNumber());

		// Assuming you get OTP from the response
		OtpEntity savedOtp = otpService.saveOtp(otpEntity);
		Map<String,Object>response = new HashMap<>();
		response.put("otpResponseDto", otpResponseDto);
		response.put("savedOtp",savedOtp);
		return new ResponseEntity<>(response, HttpStatus.OK);



    }

}
