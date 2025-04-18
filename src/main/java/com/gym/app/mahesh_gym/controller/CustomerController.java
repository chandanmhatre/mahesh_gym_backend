package com.gym.app.mahesh_gym.controller;


import com.gym.app.mahesh_gym.dto.CustomerDTO;
import com.gym.app.mahesh_gym.dto.CustomerRegistrationDTO;
import com.gym.app.mahesh_gym.entity.CustomerEntity;
import com.gym.app.mahesh_gym.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register_customer")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerRegistrationDTO customerRegistrationDTO, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }
        // If validation succeeds, proceed to map the DTO to your CustomerEntity
        String registeredCustomer = customerService.registerCustomer(customerRegistrationDTO);
        return new ResponseEntity<>(registeredCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/update_customer")
    public String updateCustomer(@RequestBody CustomerDTO customerDTO) throws Exception {
        return customerService.updateCustomer(customerDTO);
    }

    @GetMapping("/get_customer_by_id")
    public CustomerEntity findCustomerById(@RequestParam String custId) {
        return customerService.getCustomerById(custId);
    }

    @GetMapping("/get_all_customers")
    public List<CustomerEntity> getAllCustomer(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size, @RequestParam(defaultValue = "true") Boolean ascending) {
        return customerService.getAllCustomers(page, size, ascending);
    }

    @DeleteMapping("/delete_customer")
    public String deleteCustomerById(@RequestParam String custId) {
        return customerService.deleteCustomerById(custId);
    }

    @GetMapping("/get_photo")
    public Map<String, String> getPhoto(@RequestParam String custId, @RequestParam String photoType) throws IOException {
        return customerService.getPhoto(custId, photoType);
    }
}
