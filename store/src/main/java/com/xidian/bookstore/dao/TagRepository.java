package com.xidian.bookstore.dao;

import com.xidian.bookstore.entities.book.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Integer> {
    public Tag findByTagId(Integer id);
    public Tag deleteByTagId(Integer id);
}
