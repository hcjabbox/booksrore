package com.xidian.bookstore.dao;

import com.xidian.bookstore.entities.user.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Integer> {
    public VerificationCode getByEmail(String email);
    public void deleteByEmail(String email);
}
