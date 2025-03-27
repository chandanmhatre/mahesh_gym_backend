package com.gym.app.mahesh_gym.repository;

import com.gym.app.mahesh_gym.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;


@Component
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query("SELECT cust FROM CustomerEntity cust ORDER BY cust.id DESC LIMIT 1")
    CustomerEntity findLastCustomer();


}
