package com.xidian.bookstore.entities.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xidian.bookstore.entities.user.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "money")
public class Money implements Serializable {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "money_id")
    @JsonIgnore
    private Integer moneyId;
    @Column(name = "update_time")
    private Timestamp updateTime;
    @Column
    private BigDecimal balance;
    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    @JsonIgnore
    private User user;
}
