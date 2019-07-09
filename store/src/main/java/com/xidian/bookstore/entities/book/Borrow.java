package com.xidian.bookstore.entities.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xidian.bookstore.entities.user.User;
import com.xidian.bookstore.entities.user.UserAddress;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "borrow")
public class Borrow implements Serializable {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "borrow_id")
    private Integer borrowId;
    @Column(name = "borrow_time")
    private Long borrowTime;//借阅时间
    @Column
    private Integer amount;//图书数量
    @Column(name = "time_limit")
    private String timeLimit;//借阅期限
    @Column
    private String status;
    @Column(name = "damaged_degree")
    private Integer damagedDegree;
    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "book_id",referencedColumnName="book_id")
    @JsonIgnore
    private Book book;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "user_id",referencedColumnName="user_id")
    @JsonIgnore
    private User user;
    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "address_id",referencedColumnName="address_id")
    @JsonIgnore
    private UserAddress userAddress;
}
