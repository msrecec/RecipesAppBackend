package com.backend.recipes.service.user;

import com.backend.recipes.dto.user.UserDTO;
import com.backend.recipes.mapping.mapper.user.UserMapper;
import com.backend.recipes.repository.user.UserRepositoryJpa;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepositoryJpa userRepositoryJpa;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepositoryJpa userRepositoryJpa, UserMapper userMapper) {
        this.userRepositoryJpa = userRepositoryJpa;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        return userRepositoryJpa.findById(id).map(userMapper::mapUserToDTO);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepositoryJpa.findAll().stream().map(userMapper::mapUserToDTO).collect(Collectors.toList());
    }
}
