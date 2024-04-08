package com.equifarmotp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpResponseDto {
	private OtpStatus status;
    private String message;
    private String otpNumber;

    public OtpResponseDto(OtpStatus otpStatus, String otpMessage) {
    }
}
