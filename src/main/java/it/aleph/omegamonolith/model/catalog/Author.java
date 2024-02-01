package it.aleph.omegamonolith.model.catalog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="author")
public class Author {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;
    @Column(nullable=false, name="name")
    private String name;
    @Column(nullable=false, name="biography")
    private String biography;

    @ManyToMany(mappedBy = "authorList")
    private List<Book> bookList;
}
