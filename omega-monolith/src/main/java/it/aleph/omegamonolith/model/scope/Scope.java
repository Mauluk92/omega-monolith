package it.aleph.omegamonolith.model.scope;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="scope")
public class Scope {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="permission")
    @Enumerated(EnumType.STRING)
    private ScopeValue permission;
}
