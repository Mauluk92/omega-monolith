package it.aleph.omegamonolith.mapper.resource;

import it.aleph.omegamonolith.dto.resource.request.RequestedResourceOperationDto;
import it.aleph.omegamonolith.dto.resource.request.RequestedTypeOperationDto;
import it.aleph.omegamonolith.dto.resource.response.ResourceOperationDto;
import it.aleph.omegamonolith.dto.resource.response.TypeOperationDto;
import it.aleph.omegamonolith.model.resource.request.RequestedResourceOperation;
import it.aleph.omegamonolith.model.resource.request.RequestedTypeOperation;
import it.aleph.omegamonolith.model.resource.response.ResourceOperation;
import org.mapstruct.EnumMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
public interface ResourceDtoMapper {

    TypeOperationDto typeOperation(RequestedTypeOperationDto typeOperation);

    RequestedTypeOperation typeOperationEntity(RequestedTypeOperationDto typeOperationDto);

    @Mapping(source="requestedTypeOperationDto", target="requestedTypeOperation")
    RequestedResourceOperation toEntity(RequestedResourceOperationDto dto);

    RequestedResourceOperationDto toDto(RequestedResourceOperation entity);
    @Mapping(source="requestedTypeOperation", target="typeOperationDto")
    ResourceOperationDto toResponse(RequestedResourceOperation entityRequest);

    @Mapping(source="typeOperationDto", target="typeOperation")
    @Mapping(source="operationStatusDto", target="operationStatus")
    ResourceOperation toEntity(ResourceOperationDto dto);
}
