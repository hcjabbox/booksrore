package com.xidian.bookstore.dao;

import com.xidian.bookstore.entities.book.Book;
import com.xidian.bookstore.entities.book.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PictureRepositiory extends JpaRepository<Picture,Integer> {
    Picture findByPictureId(Integer id);
    void deleteByPictureId(Integer id);
    List<Picture> findAllByBook(Book book);
}
