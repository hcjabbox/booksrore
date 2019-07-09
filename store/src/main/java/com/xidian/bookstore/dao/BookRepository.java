package com.xidian.bookstore.dao;

import com.xidian.bookstore.entities.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Integer> {

    public Book findByBookId(Integer id);
    public void deleteByBookId(Integer id);
}
