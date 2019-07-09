package com.xidian.bookstore.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xidian.bookstore.entities.book.Borrow;
import com.xidian.bookstore.entities.order.Order;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "address")
public class UserAddress implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "address_id")
    private Integer addressId;
    @Column
    private String province;
    @Column
    private String city;
    @Column
    private String region;
    @Column
    private String addr;//详细街道
    @Column
    private String mobile;//收货人手机号
    @Column
    private String receiver;//收货人姓名
    @Column
    private Timestamp creatime;
    @Column
    private String flag;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "user_id",referencedColumnName="user_id")
    private User user;
    @JsonIgnore
    @OneToOne(mappedBy = "userAddress")
    private Order order;
    @JsonIgnore
    @OneToOne(mappedBy = "userAddress")
    private Borrow borrow;
}
