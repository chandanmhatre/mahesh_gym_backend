package com.gym.app.mahesh_gym.service.impl;

import com.gym.app.mahesh_gym.dto.CustomerDTO;
import com.gym.app.mahesh_gym.entity.CustomerEntity;
import com.gym.app.mahesh_gym.mapper.CustomerMapper;
import com.gym.app.mahesh_gym.repository.CustomerRepository;
import com.gym.app.mahesh_gym.service.CustomerService;
import com.gym.app.mahesh_gym.utils.FileStorageUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public String registerCustomer(CustomerDTO customerDTO) throws IOException {
        CustomerEntity customerEntity = CustomerMapper.toEntity(customerDTO);
        customerRepository.save(customerEntity);
        CustomerEntity customerEntity1 = customerRepository.findLastCustomer();
        HashMap<String, String> custPhoto = customerDTO.getCustomerPhoto();
        String custfileName = String.valueOf(custPhoto.keySet().stream().findFirst().orElse(""));
        String custfileBase64 = String.valueOf(custPhoto.values().stream().findFirst().orElse(""));
        FileStorageUtil.saveBase64ToFile(customerEntity1.getId().toString(), custfileBase64, "customer_" + custfileName);

        HashMap<String, String> docPhoto = customerDTO.getDocumentPhoto();
        String docfileName = String.valueOf(docPhoto.keySet().stream().findFirst().orElse(""));
        String docfileBase64 = String.valueOf(docPhoto.values().stream().findFirst().orElse(""));
        FileStorageUtil.saveBase64ToFile(customerEntity1.getId().toString(), docfileBase64, "document_" + docfileName);
        return "Customer created successfully";
    }

    @Override
    public String updateCustomer(CustomerDTO customerDTO) {
//        CustomerEntity customerEntity1 = customerRepository.findById(customerEntity.getId()).orElseThrow(() -> new RuntimeException("Customer not found"));
//        EntityUtils.mergeNonNullProperties(customerEntity, customerEntity1);
//        customerRepository.save(customerEntity);
        return "Customer updated successfully";
    }

    @Override
    public CustomerEntity getCustomerById(String id) {
        Long custId = Long.parseLong(id);
        return customerRepository.findById(custId).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public String deleteCustomerById(String id) {
        Long custId = Long.parseLong(id);
        customerRepository.deleteById(custId);
        return "Deleted successfully";
    }
}
