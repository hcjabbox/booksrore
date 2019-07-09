package com.xidian.bookstore.entities.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xidian.bookstore.entities.order.Order;
import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "pay")
public class Pay implements Serializable {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "payment_id")
    private Integer paymentId;//支付编号
    @Column(name = "payment_number")
    private String paymentNumber;//支付单号
    @Column
    private String type;//支付类型
    @Column(name = "pay_time")
    private Timestamp payTime;//付款时间
    @OneToOne(mappedBy = "pay")
    @JsonIgnore
    private Order order;
}
