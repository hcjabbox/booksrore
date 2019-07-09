package com.xidian.bookstore.entities.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "logistics")
public class Logistics implements Serializable {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "logistics_id")
    private Integer logisticsId;
    @Column(name = "company_name")
    private String companyNme;
    @Column
    private String description;
    @Column(name = "logisticsNumber")
    private String logisticsNumber;//物流单号
    @Column(name = "deliver_time")
    private String deliverTime;//发货时间
    @OneToOne(mappedBy = "logistics")
    @JsonIgnore
    private Order order;
    @Column(name = "create_time")
    private Timestamp createTime;
}
