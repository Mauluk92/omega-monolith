package it.aleph.omegamonolith.kafka;

import it.aleph.omegamonolith.dao.resource.RequestedResourceRepository;
import it.aleph.omegamonolith.dto.resource.request.RequestedResourceOperationDto;
import it.aleph.omegamonolith.mapper.resource.ResourceDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
@RequiredArgsConstructor
public class RequestOperationKafkaListener {

    private final ResourceDtoMapper resourceDtoMapper;
    private final RequestedResourceRepository repository;

    @KafkaListener(containerFactory = "listenerRequestFactory" , topics="report-book-events")
    public void listen(@Payload @Valid RequestedResourceOperationDto resourceRequestOperation){
        repository.save(resourceDtoMapper.toEntity(resourceRequestOperation));
    }
}
