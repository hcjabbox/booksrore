package com.xidian.bookstore.entities.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xidian.bookstore.entities.book.Book;
import com.xidian.bookstore.entities.book.Comment;
import com.xidian.bookstore.entities.payment.Pay;
import com.xidian.bookstore.entities.user.User;
import com.xidian.bookstore.entities.user.UserAddress;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "b_order")
public class Order implements Serializable {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "order_id")
    private Integer orderId;
    @Column
    private Integer status;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "user_id",referencedColumnName="user_id")
    private User user;
    @Column
    private Integer amount;
    @Column
    private BigDecimal price;
    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "logistics_id",referencedColumnName = "logistics_id")
    private Logistics logistics;//物流编号
    @OneToOne
    @JoinColumn(name = "address_id",referencedColumnName = "address_id")
    private UserAddress userAddress;
    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "comment_id",referencedColumnName = "comment_id")
    @JsonIgnore
    private Comment comment;
    @OneToOne
    @JoinColumn(name = "payment_id",referencedColumnName = "payment_id")//支付编号
    @JsonIgnore
    private Pay pay;
    @Column(name = "create_time")
    private Timestamp creatime;//创建时间
    @Column(name = "complete_time")
    private Timestamp completeTime;//成交时间，当用户确认收货时的时间
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "book_id",referencedColumnName="book_id")
    @JsonIgnore
    private Book book;
    @Column
    private String description;

}

