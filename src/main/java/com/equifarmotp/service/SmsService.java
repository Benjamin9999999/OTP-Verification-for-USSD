package com.equifarmotp.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.equifarmotp.entity.OtpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equifarmotp.config.TwilioConfig;
import com.equifarmotp.dto.OtpRequest;
import com.equifarmotp.dto.OtpResponseDto;
import com.equifarmotp.dto.OtpStatus;
import com.equifarmotp.dto.OtpValidationRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SmsService {

	@Autowired
	private TwilioConfig twilioConfig;
    Map<String, String> otpMap = new HashMap<>();


	public OtpResponseDto sendSMS(OtpRequest otpRequest) {
		OtpEntity otpEntity = new OtpEntity();
        new OtpResponseDto();
        OtpResponseDto otpResponseDto = new OtpResponseDto();
		try {
			String generatedOtp= generateOTP();
			otpEntity.setOtpNumber(generatedOtp);
			PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());//to
			PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber()); // from
//			String otp = generateOTP();
			String otpMessage = "Dear Customer , Your OTP is  " + generatedOtp + " for Equifarm Registration. Thank You.";
			Message message = Message
			        .creator(to, from,
			                otpMessage)
			        .create();
			otpMap.put(otpRequest.getUsername(), generatedOtp);
			otpResponseDto.setMessage("OTP has been sent successfully ! ");
			otpResponseDto.setOtpNumber(generatedOtp);
			otpResponseDto.setStatus(OtpStatus.DELIVERED);

			System.out.println("this is the response" +otpResponseDto);
		} catch (Exception e) {
			e.printStackTrace();
			otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
		}
		return otpResponseDto;
	}
	
	public String validateOtp(OtpValidationRequest otpValidationRequest) {
		Set<String> keys = otpMap.keySet();
		String username = null;
		for(String key : keys)
			username = key;
        if (otpValidationRequest.getUsername().equals(username)) {
            otpMap.remove(username,otpValidationRequest.getOtpNumber());
            return "OTP is valid!";
        } else {
            return "OTP is invalid!";
        }
	}
	

public String generateOTP() {
		return new DecimalFormat("000000")
				.format(new Random().nextInt(999999));

}

}
