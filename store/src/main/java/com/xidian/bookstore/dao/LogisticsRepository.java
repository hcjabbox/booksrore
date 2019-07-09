package com.xidian.bookstore.dao;

import com.xidian.bookstore.entities.order.Logistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogisticsRepository extends JpaRepository<Logistics,Integer> {
    Logistics findByLogisticsId(Integer id);
    void deleteByLogisticsId(Integer id);
}
