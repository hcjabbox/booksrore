package com.xidian.bookstore.dao;

import com.xidian.bookstore.entities.book.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    public Category findByCategoryId(Integer id);
    public void deleteByCategoryId(Integer id);
}
