package it.aleph.omegamonolith.config.resource.batch.processor;

import it.aleph.omegamonolith.dto.resource.response.ResourceOperationDto;
import it.aleph.omegamonolith.mapper.resource.ResourceDtoMapper;
import it.aleph.omegamonolith.model.resource.response.ResourceOperation;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceDtoToEntityProcessor implements ItemProcessor<ResourceOperationDto, ResourceOperation> {

    private final ResourceDtoMapper mapper;

    @Override
    public ResourceOperation process(@Nonnull ResourceOperationDto item) throws Exception {
        return mapper.toEntity(item);
    }
}
