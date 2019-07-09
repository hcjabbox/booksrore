package com.xidian.bookstore.entities.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "category")
public class Category implements Serializable {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "category_id")
    private Integer categoryId;
    @Column
    private String name;
    @Column
    private String description;
    @Column(name = "create_time")
    private Timestamp creatime;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Book> books = new HashSet<>();
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Tag> tags = new HashSet<>();
}
