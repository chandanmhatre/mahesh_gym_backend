package com.gym.app.mahesh_gym.controller;


import com.gym.app.mahesh_gym.dto.CustomerDTO;
import com.gym.app.mahesh_gym.entity.CustomerEntity;
import com.gym.app.mahesh_gym.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register_customer")
    public String registerCustomer(@RequestBody CustomerDTO customerDTO) throws IOException {
        return customerService.registerCustomer(customerDTO);
    }

    @PutMapping("/update_customer")
    public String updateCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(customerDTO);
    }

    @GetMapping("/get_customer_by_id")
    public CustomerEntity findCustomerById(@RequestParam String id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/get_all_customers")
    public List<CustomerEntity> getAllCustomer() {
        return customerService.getAllCustomers();
    }

    @DeleteMapping("/delete_customer")
    public String deleteCustomerById(@RequestParam String id) {
        return customerService.deleteCustomerById(id);
    }
}
