package it.aleph.omegamonolith.model.resource.response;

import it.aleph.omegamonolith.model.resource.converter.ResourceConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name="resource_operation")
public class ResourceOperation {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="type_operation", nullable= false)
    @Enumerated(EnumType.STRING)
    private TypeOperation typeOperation;
    @Column(name="operation_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationStatus operationStatus;
    @Column(name="additional_information")
    private String additionalInfo;
    @Column(name="timestamp", nullable = false)
    private Instant timestamp;
    @Column(name="address")
    private String address;
    @Column(name="resource")
    @Convert(converter = ResourceConverter.class)
    private Map<String, Object> resource;

}
