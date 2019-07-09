package com.xidian.bookstore.dao;

import com.xidian.bookstore.entities.payment.Money;
import com.xidian.bookstore.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyRepository extends JpaRepository<Money,Integer> {
    Money findByUser(User user);
}
