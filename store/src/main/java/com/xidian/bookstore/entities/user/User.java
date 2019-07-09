package com.xidian.bookstore.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xidian.bookstore.entities.book.Borrow;
import com.xidian.bookstore.entities.book.Collect;
import com.xidian.bookstore.entities.order.Order;
import com.xidian.bookstore.entities.order.ShoppingCart;
import com.xidian.bookstore.entities.payment.Money;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Long userId;
    @Column
    private String uname;
    @JsonIgnore
    @Column
    private String password;
    @Column
    @Email
    private String email;
    @Column
    private String mobile;
    @Column
    private String sex;
    @Column(name = "reg_time")
    private Timestamp regTime;//注册时间
    @Column
    private String image;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<UserAddress> addressSet = new HashSet<>();
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Order> orders = new HashSet<>();
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<ShoppingCart> carts = new HashSet<>();
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Collect> collects = new HashSet<>();
    @OneToOne(mappedBy = "user")
    private Money money;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Borrow> borrows = new HashSet<>();
}
