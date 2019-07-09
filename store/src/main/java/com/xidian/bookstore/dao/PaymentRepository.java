package com.xidian.bookstore.dao;

import com.xidian.bookstore.entities.payment.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Pay,Integer> {
    Pay findByPaymentId(Integer id);
    void deleteByPaymentId(Integer id);
}
