package it.aleph.omegamonolith.model.user;

import it.aleph.omegamonolith.model.group.Group;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="user")
public class User {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="username")
    private String username;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_group",
            joinColumns =@JoinColumn(name="user_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="group_id",
                    referencedColumnName = "id") )
    private List<Group> groupList;
}
