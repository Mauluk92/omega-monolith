package it.aleph.omegamonolith.config.catalog.batch;

import it.aleph.omegamonolith.dto.resource.request.RequestedResourceOperationDto;
import it.aleph.omegamonolith.dto.resource.response.OperationStatusDto;
import it.aleph.omegamonolith.dto.resource.response.ResourceOperationDto;
import it.aleph.omegamonolith.mapper.resource.ResourceDtoMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class RequestItemProcessor implements ItemProcessor<RequestedResourceOperationDto, ResourceOperationDto> {

    private final ResourceDtoMapper mapper;


    @Override
    public ResourceOperationDto process(@Nonnull RequestedResourceOperationDto item) throws Exception {
        ResourceOperationDto resourceOperationDto = new ResourceOperationDto();
        resourceOperationDto.setOperationStatusDto(OperationStatusDto.SUCCESS);
        resourceOperationDto.setTypeOperationDto(mapper.typeOperation(item.getRequestedTypeOperationDto()));
        resourceOperationDto.setTimestamp(Instant.now());
        return resourceOperationDto;
    }
}
