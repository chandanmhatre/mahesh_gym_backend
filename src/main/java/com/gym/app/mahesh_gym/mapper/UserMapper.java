package com.gym.app.mahesh_gym.mapper;

import com.gym.app.mahesh_gym.dto.UserDTO;
import com.gym.app.mahesh_gym.entity.UserEntity;

public class UserMapper {
    public static UserDTO toDto(UserEntity userEntity) {
        return new UserDTO(userEntity.getUserId(), userEntity.getUserName(), userEntity.getMobile(), userEntity.getEmail(), userEntity.getJoiningDate(), userEntity.getIsActive());
    }

    public static UserEntity toEntity(UserDTO userDTO, String password) {
        return new UserEntity(userDTO.getUserId(), userDTO.getUserName(), password, userDTO.getMobile(), userDTO.getEmail(), userDTO.getJoiningDate(), userDTO.getIsActive());
    }
}
