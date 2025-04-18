package com.gym.app.mahesh_gym.service;

import com.gym.app.mahesh_gym.dto.CustomerDTO;
import com.gym.app.mahesh_gym.dto.CustomerRegistrationDTO;
import com.gym.app.mahesh_gym.entity.CustomerEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CustomerService {

    String registerCustomer(CustomerRegistrationDTO customerRegistrationDTO) throws IOException;

    String updateCustomer(CustomerDTO customerDTO) throws IOException;

    CustomerEntity getCustomerById(String id);

    List<CustomerEntity> getAllCustomers(Integer page, Integer size, Boolean ascending);

    String deleteCustomerById(String id);

    Map<String, String> getPhoto(String id, String photoType) throws IOException;
}
