package it.aleph.omegamonolith.mapper.user;

import it.aleph.omegamonolith.dto.user.UserDto;
import it.aleph.omegamonolith.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
public interface UserDtoMapper {

    User toEntity(UserDto userDto);
    UserDto toDto(User user);
}
