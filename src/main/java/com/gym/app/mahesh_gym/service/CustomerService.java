package com.gym.app.mahesh_gym.service;

import com.gym.app.mahesh_gym.dto.CustomerDTO;
import com.gym.app.mahesh_gym.entity.CustomerEntity;

import java.io.IOException;
import java.util.List;

public interface CustomerService {

    public String registerCustomer(CustomerDTO customerDTO) throws IOException;

    public String updateCustomer(CustomerDTO customerDTO);

    public CustomerEntity getCustomerById(String id);

    public List<CustomerEntity> getAllCustomers();

    public String deleteCustomerById(String id);
}
