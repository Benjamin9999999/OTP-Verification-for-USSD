package com.equifarmotp.service;

import com.equifarmotp.entity.OtpEntity;
import org.springframework.stereotype.Service;

@Service

public interface OtpService {
    OtpEntity saveOtp(OtpEntity otpEntity);
    OtpEntity validateOtp(OtpEntity otpEntity);

}
