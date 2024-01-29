package it.aleph.omegamonolith.config.resource.batch.processor;

import it.aleph.omegamonolith.dto.resource.request.RequestedResourceOperationDto;
import it.aleph.omegamonolith.mapper.resource.ResourceDtoMapper;
import it.aleph.omegamonolith.model.resource.request.RequestedResourceOperation;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RequestResourceDtoMapper implements ItemProcessor<RequestedResourceOperationDto, RequestedResourceOperation> {

    private final ResourceDtoMapper resourceMapper;


    @Override
    public RequestedResourceOperation process(@Nonnull RequestedResourceOperationDto item) throws Exception {
        return resourceMapper.toEntity(item);
    }
}
