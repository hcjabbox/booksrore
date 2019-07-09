package com.xidian.bookstore.entities.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xidian.bookstore.entities.user.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "collect")
public class Collect implements Serializable {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "collect_id")
    private Integer collectId;
    @Column(name = "create_time")
    private Timestamp creatime;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "user_id",referencedColumnName="user_id")
    @JsonIgnore
    private User user;
    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "book_id",referencedColumnName = "book_id")
    @JsonIgnore
    private Book book;
    @Column
    private String description;
}
