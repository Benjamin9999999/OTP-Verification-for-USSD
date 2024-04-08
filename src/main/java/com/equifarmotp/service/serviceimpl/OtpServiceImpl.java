package com.equifarmotp.service.serviceimpl;

import com.equifarmotp.entity.OtpEntity;
import com.equifarmotp.repo.OtpRepo;
import com.equifarmotp.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class OtpServiceImpl implements OtpService {
    @Autowired
    private final OtpRepo otpRepo;

    public OtpServiceImpl(OtpRepo otpRepo) {
        this.otpRepo = otpRepo;
    }

    @Override
    public OtpEntity saveOtp(OtpEntity otpEntity) {

        return otpRepo.save(otpEntity);
    }

    @Override
    public OtpEntity validateOtp(OtpEntity otpEntity) {
        return null;
    }
}
