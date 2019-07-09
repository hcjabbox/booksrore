package com.xidian.bookstore.entities.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "unit_price")
public class UnitPrice implements Serializable {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "price_id")
    private Integer priceId;
    @Column
    private BigDecimal price;
    @Column
    private String status;//表明是哪一个的单价如borrow代表借书的日价格或者damage代表损坏书籍的每损坏度赔偿价格
    @Column(name = "update_time")
    private Timestamp updateTime;
}
