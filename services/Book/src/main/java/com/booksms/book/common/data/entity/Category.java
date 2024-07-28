package com.booksms.book.common.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class Category extends AbstractEntity{
    @Id
    private int id;
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY,mappedBy = "category")
    private List<Book> books;
}
