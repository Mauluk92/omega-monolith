package it.aleph.omegamonolith.model.catalog;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(nullable=false, name="title")
    private String title;
    @Column(nullable=false, name="isbn")
    private String isbn;
    @Column(nullable=false, name="dewey_decimal_code")
    private String deweyDecimalCode;
    @Column(nullable=false, name="description")
    private String description;
    @Column(nullable=false, name="pub_date")
    private LocalDate pubDate;
    @Column(nullable=false, name="pub_house")
    private String pubHouse;
    @Column(nullable=false, name="availability")
    private Boolean availability;
    @Column(nullable =false, name="cutter_number")
    private String cutterNumber;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "book_author",
            joinColumns =@JoinColumn(name="book_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="author_id",
                    referencedColumnName = "id") )
    private List<Author> authorList;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "book_tag",
            joinColumns =@JoinColumn(name="book_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="tag_id",
                    referencedColumnName = "id") )
    private List<Tag> tagList;

}
