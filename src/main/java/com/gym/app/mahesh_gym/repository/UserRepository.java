package com.gym.app.mahesh_gym.repository;

import com.gym.app.mahesh_gym.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
