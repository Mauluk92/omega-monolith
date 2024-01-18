package it.aleph.omegamonolith.model.catalog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="tag")
public class Tag {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;
    @Column(nullable=false, name="name")
    private String name;
    @Column(nullable=false, name="description")
    private String description;
    @ManyToMany(mappedBy = "tagList")
    private List<Book> taggedBookList;
}
