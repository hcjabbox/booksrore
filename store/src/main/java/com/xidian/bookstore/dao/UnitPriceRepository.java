package com.xidian.bookstore.dao;

import com.xidian.bookstore.entities.book.UnitPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitPriceRepository extends JpaRepository<UnitPrice,Integer> {
    UnitPrice findByPriceId(Integer id);
    UnitPrice findByStatus(String status);
}
