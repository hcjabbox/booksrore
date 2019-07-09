package com.xidian.bookstore.dao;

import com.xidian.bookstore.entities.book.Collect;
import com.xidian.bookstore.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectRepository extends JpaRepository<Collect,Integer> {

    public List<Collect> findAllByUser(User user);
    public void deleteByCollectId(Integer id);
    public Collect findByCollectId(Integer id);
}
