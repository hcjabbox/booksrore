package com.xidian.bookstore.entities.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xidian.bookstore.entities.book.Book;
import com.xidian.bookstore.entities.user.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "cart")
public class ShoppingCart implements Serializable {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "cart_id")
    private Integer cartId;
    @Column
    private Integer amount;
    @Column
    private BigDecimal price;
    @Column(name = "create_time")
    private Timestamp creatime;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    @JsonIgnore
    private User user;
    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "book_id",referencedColumnName = "book_id")
    @JsonIgnore
    private Book book;
    
}
