package it.aleph.omegamonolith.service.user;

import it.aleph.omegamonolith.dto.user.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDto);
    void removeUser(Long id);
    UserDto findUserById(Long id);
}
