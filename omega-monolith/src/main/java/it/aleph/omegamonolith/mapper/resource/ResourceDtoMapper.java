package it.aleph.omegamonolith.mapper.resource;

import it.aleph.omegamonolith.dto.resource.request.RequestedResourceOperationDto;
import it.aleph.omegamonolith.dto.resource.request.RequestedTypeOperationDto;
import it.aleph.omegamonolith.dto.resource.response.TypeOperationDto;
import it.aleph.omegamonolith.model.resource.request.RequestedResourceOperation;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
public interface ResourceDtoMapper {

    TypeOperationDto typeOperation(RequestedTypeOperationDto typeOperation);

    RequestedResourceOperation toEntity(RequestedResourceOperationDto dto);
}
