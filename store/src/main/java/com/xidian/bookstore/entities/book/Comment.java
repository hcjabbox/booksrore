package com.xidian.bookstore.entities.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xidian.bookstore.entities.order.Order;
import com.xidian.bookstore.entities.user.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;

@Data
@Entity
@Table(name = "comment")
public class Comment implements Serializable {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "comment_id")
    private Integer commentId;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "user_id",referencedColumnName="user_id")
    private User user;
    @OneToOne(mappedBy = "comment")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "book_id",referencedColumnName="book_id")
    private Book book;
    @Column
    private String comment;
    @Column(name = "create_time")
    private Timestamp createTime;
}
