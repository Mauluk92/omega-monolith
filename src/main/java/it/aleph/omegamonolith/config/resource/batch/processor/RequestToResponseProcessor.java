package it.aleph.omegamonolith.config.resource.batch.processor;

import it.aleph.omegamonolith.dao.resource.RequestedResourceRepository;
import it.aleph.omegamonolith.dto.resource.request.RequestedResourceOperationDto;
import it.aleph.omegamonolith.dto.resource.response.OperationStatusDto;
import it.aleph.omegamonolith.dto.resource.response.ResourceOperationDto;
import it.aleph.omegamonolith.mapper.resource.ResourceDtoMapper;
import it.aleph.omegamonolith.model.resource.request.RequestedResourceOperation;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@RequiredArgsConstructor
@Component
public class RequestToResponseProcessor implements ItemProcessor<RequestedResourceOperation, ResourceOperationDto> {

    private final ResourceDtoMapper resourceMapper;
    private final RequestedResourceRepository requestedResourceRepository;



    @Override
    public ResourceOperationDto process(@Nonnull RequestedResourceOperation item) throws Exception {
        ResourceOperationDto resource =  resourceMapper.toResponse(item);
        resource.setTimestamp(Instant.now());
        resource.setOperationStatusDto(OperationStatusDto.SUCCESS);
        if(!item.getValid()){
            resource.setOperationStatusDto(OperationStatusDto.REJECTED);
        }
        item.setExecuted(true);
        requestedResourceRepository.save(item);
        return resource;
    }
}
