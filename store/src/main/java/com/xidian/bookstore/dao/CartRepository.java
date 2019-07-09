package com.xidian.bookstore.dao;

import com.xidian.bookstore.entities.order.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<ShoppingCart,Integer> {
    public ShoppingCart findByCartId(Integer id);
    public void deleteByCartId(Integer id);
}
