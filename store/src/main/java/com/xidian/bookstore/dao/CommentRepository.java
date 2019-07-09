package com.xidian.bookstore.dao;

import com.xidian.bookstore.entities.book.Book;
import com.xidian.bookstore.entities.book.Comment;
import com.xidian.bookstore.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Comment findByCommentId(Integer id);
    void deleteByCommentId(Integer id);
    List<Comment> findAllByBook(Book book);
    List<Comment> findAllByUser(User user);
}
