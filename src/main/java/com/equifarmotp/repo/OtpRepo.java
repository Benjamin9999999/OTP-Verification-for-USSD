package com.equifarmotp.repo;

import com.equifarmotp.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepo extends JpaRepository<OtpEntity,Long> {

}
