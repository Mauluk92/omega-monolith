package it.aleph.omegamonolith.model.resource.request;

import it.aleph.omegamonolith.model.catalog.Book;
import it.aleph.omegamonolith.model.resource.converter.ResourceConverter;
import it.aleph.omegamonolith.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Entity
@Table(name="request_operation")
public class RequestedResourceOperation {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="requested_type_operation", nullable= false)
    @Enumerated(EnumType.STRING)
    private RequestedTypeOperation requestedTypeOperation;
    @Column(name="resource_type", nullable = false)
    private String resourceType;
    @Column(name="additional_information", nullable = false)
    private String additionalInformation;
    @Column(name="valid", nullable = false)
    private Boolean valid;
    @Column(name="resource", nullable = false)
    @Convert(converter = ResourceConverter.class)
    private Map<String, Object> resource;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User associatedUser;
    
}
