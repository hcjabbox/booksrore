package com.xidian.bookstore.entities.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xidian.bookstore.entities.order.Order;
import com.xidian.bookstore.entities.order.ShoppingCart;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "book")
public class Book implements Serializable {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "book_id")
    private Integer bookId;
    @Column(name = "book_name")
    private String bookName;
    @Column
    private BigDecimal price;
    @Column(name = "publisher_name")
    private String publisherName;
    @Column
    private String author;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "category_id",referencedColumnName="category_id")
    private Category category;
    @Column
    private Integer inventory;//库存
    @Column
    private String intro;//简介
    @Column(name = "create_time")
    private Timestamp creatime;//后台上传图书信息的时间
    @Column(name = "browse_number")
    private Integer browseNumber;
    @Column(name = "purchase_number")
    private Integer purchaseNumber;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "tag_id",referencedColumnName="tag_id")
    @JsonIgnore
    private Tag tag;
    @OneToOne(mappedBy = "book")
    @JsonIgnore
    private ShoppingCart cart;
    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private Set<Order> orders = new HashSet<>();
    @OneToOne(mappedBy = "book")
    @JsonIgnore
    private Collect collect;
    @OneToMany(mappedBy = "book")
    private Set<Comment> comments = new HashSet<>();
    @OneToMany(mappedBy = "book")
    private Set<Picture> pictures = new HashSet<>();
    @OneToOne(mappedBy = "book")
    private Borrow borrow;

}
