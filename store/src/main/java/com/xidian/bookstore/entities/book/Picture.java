package com.xidian.bookstore.entities.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "picture")
public class Picture implements Serializable {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "picture_id")
    private Integer pictureId;
    @Column(name = "picture_string")
    private String pictureString;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "book_id",referencedColumnName="book_id")
    @JsonIgnore
    private Book book;
}
