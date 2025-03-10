package com.gym.app.mahesh_gym.service;

import com.gym.app.mahesh_gym.dto.UserDTO;
import com.gym.app.mahesh_gym.dto.UserRegistrationDto;

import java.util.List;

public interface UserService {

    String createUser(UserRegistrationDto userRegistrationDto);

    String updateUser(UserDTO userDTO);

    UserDTO getUser(String id);

    List<UserDTO> getAllUsers();

    List<UserDTO> getActiveUsers();
}
