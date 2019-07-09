package com.xidian.bookstore.entities.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "admin")
public class Administrator implements Serializable {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "admin_id")
    private Integer adminId;
    @Column
    private String account;
    @Column
    private String password;
    @Column(name = "create_time")
    private Timestamp createTime;
}
