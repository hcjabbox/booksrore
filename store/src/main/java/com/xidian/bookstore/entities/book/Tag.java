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
@Table(name = "tag")
public class Tag implements Serializable {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "tag_id")
    private Integer tagId;
    @Column
    private String name;
    @Column(name = "create_time")
    private Timestamp creatime;
    @Column
    private String description;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "category_id",referencedColumnName="category_id")
    @JsonIgnore
    private Category category;
    @OneToMany(mappedBy = "tag")
    @JsonIgnore
    private Set<Book> books = new HashSet<>();

}
