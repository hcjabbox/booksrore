package com.xidian.bookstore.dao;

import com.xidian.bookstore.entities.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    public void deleteByOrderId(Integer id);
    public Order findByOrderId(Integer id);
    public List<Order> findAllByStatus(Integer status);
}
