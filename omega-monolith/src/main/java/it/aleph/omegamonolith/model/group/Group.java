package it.aleph.omegamonolith.model.group;

import it.aleph.omegamonolith.model.scope.Scope;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="group")
public class Group {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false)
    private String name;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "group_scope",
            joinColumns =@JoinColumn(name="group_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="scope_id",
                    referencedColumnName = "id") )
    private List<Scope> scopeList;
}
