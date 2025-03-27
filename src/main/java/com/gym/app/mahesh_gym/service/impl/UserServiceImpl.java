package com.gym.app.mahesh_gym.service.impl;


import com.gym.app.mahesh_gym.dto.UserDTO;
import com.gym.app.mahesh_gym.dto.UserRegistrationDto;
import com.gym.app.mahesh_gym.entity.UserEntity;
import com.gym.app.mahesh_gym.mapper.UserMapper;
import com.gym.app.mahesh_gym.repository.UserRepository;
import com.gym.app.mahesh_gym.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String createUser(UserRegistrationDto userRegistrationDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userRegistrationDto.getUserName());
        userEntity.setMobile(userRegistrationDto.getMobile());
        userEntity.setEmail(userRegistrationDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        userRepository.save(userEntity);
        return "Saved Successful";
    }

    @Override
    public String updateUser(UserDTO userDTO) {
        UserEntity userEntity = userRepository.findById(userDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
//        userEntity.setUserName(userDTO.getUserName());
        userEntity.setMobile(userDTO.getMobile());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setIsActive(userDTO.getIsActive());
        userRepository.save(userEntity);
        return "Update Successful";
    }

    @Override
    public UserDTO getUser(String id) {
        Long userId = Long.parseLong(id);
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toDto(userEntity);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getActiveUsers() {
        return userRepository.findAll().stream().map(UserMapper::toDto).filter(UserDTO::getIsActive).collect(Collectors.toList());
//        List<UserDTO> inactiveUsers = userRepository.findAll().stream()
//                .map(UserMapper::toDto)
//                .filter(Predicate.not(UserDTO::getIsActive)) // Functional reference with negation
//                .collect(Collectors.toList());
    }

}
