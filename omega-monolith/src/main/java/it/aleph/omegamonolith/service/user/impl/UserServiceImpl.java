package it.aleph.omegamonolith.service.user.impl;

import it.aleph.omegamonolith.dao.user.UserRepository;
import it.aleph.omegamonolith.dto.user.UserDto;
import it.aleph.omegamonolith.exception.NotFoundException;
import it.aleph.omegamonolith.mapper.user.UserDtoMapper;
import it.aleph.omegamonolith.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        return userDtoMapper.toDto(userRepository.save(userDtoMapper.toEntity(userDto)));
    }

    @Override
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto findUserById(Long id) {
        return userDtoMapper
                .toDto(userRepository
                        .findById(id).orElseThrow(() -> NotFoundException.builder()
                                .idListNotFound(List.of(id))
                                .message("The following user was not found: " +  id)
                                .build()));
    }
}
