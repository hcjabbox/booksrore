package com.xidian.bookstore.dao;

import com.xidian.bookstore.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User,Integer> {

    public void deleteByUserId(Long userId);
    public User findByUserId(Long id);
    public User getByEmailAndPassword(String email,String password);
    public User getByEmail(String email);

}
