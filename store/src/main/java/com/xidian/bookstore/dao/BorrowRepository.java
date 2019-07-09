package com.xidian.bookstore.dao;

import com.xidian.bookstore.entities.book.Borrow;
import com.xidian.bookstore.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow,Integer> {
    Borrow findByBorrowId(Integer id);
    void deleteByBorrowId(Integer id);
    List<Borrow> findAllByUser(User user);
}
