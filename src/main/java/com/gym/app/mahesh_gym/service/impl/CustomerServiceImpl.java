package com.gym.app.mahesh_gym.service.impl;

import com.gym.app.mahesh_gym.dto.CustomerDTO;
import com.gym.app.mahesh_gym.dto.CustomerRegistrationDTO;
import com.gym.app.mahesh_gym.entity.CustomerEntity;
import com.gym.app.mahesh_gym.mapper.CustomerMapper;
import com.gym.app.mahesh_gym.repository.CustomerRepository;
import com.gym.app.mahesh_gym.service.CustomerService;
import com.gym.app.mahesh_gym.utils.EntityUtils;
import com.gym.app.mahesh_gym.utils.FileStorageUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    //    Why @Transactional is appropriate here:
    //    Multiple Operations: Your registerCustomer method performs at least two main operations:
    //    Saving the CustomerEntity to the database using customerRepository.save().
    //    Saving images to the file system using saveImages().
    //    Atomicity: You likely want these two operations to be atomic in a logical sense. If saving the customer to the database fails,
    //              you might want to avoid saving the images, or vice versa, to prevent inconsistencies (e.g., a customer record without associated images,
    //              or orphaned images without a corresponding customer).
    //    Data Consistency: By wrapping these operations in a transaction, you ensure that if any exception occurs during either the database save or the image saving process,
    //              the entire transaction can be rolled back. This prevents partial data creation and maintains the integrity of your application's state.

    //    How it works with @Transactional:
    //      1) When registerCustomer is called, Spring's transaction management will start a transaction (if one isn't already active).
    //      2) The customerRepository.save() operation will be executed within this transaction.
    //      3) The saveImages() method will also execute within the same transaction context.
    //      4) If both operations complete successfully without throwing any exceptions, the transaction will be committed, and all changes
    //         (both to the database and the file system) will be finalized.
    //      5) If an IOException occurs in saveImages() (as declared in the method signature) or if customerRepository.save() throws an exception,
    //          Spring will, by default, mark the transaction for rollback. The database changes made by save() will be rolled back,
    //         and the overall operation will fail, preventing inconsistencies.

    //    Ensure that you have a properly configured transaction manager in your Spring application context
    //    (e.g., JpaTransactionManager when using JPA with Hibernate).
    //    Spring Boot usually auto-configures this for you based on your data source.
    @Transactional
    @Override
    public String registerCustomer(CustomerRegistrationDTO customerRegistrationDTO) throws IOException {
        CustomerEntity customerEntity = CustomerMapper.toEntity(customerRegistrationDTO);
        CustomerEntity customerEntity2 = customerRepository.save(customerEntity);
        saveImages(customerRegistrationDTO, customerEntity2.getCustId().toString());
        return "Customer created successfully";
    }


    /*
     * Reasons to Use @Transactional for a Single Data Manipulation:
     *
     * 1) Consistency and Atomicity (Even for One Operation):
     * Even a single save(), update(), or delete() operation might involve multiple
     * underlying steps at the database level. @Transactional ensures that this
     * single logical operation is treated as an atomic unit. If any part of the
     * underlying process fails, the entire operation can be rolled back, preventing
     * potential inconsistencies.
     *
     * 2) Exception Handling and Rollback:
     * If the single data manipulation throws an exception, @Transactional will
     * automatically trigger a rollback of that operation. This is crucial for
     * maintaining data integrity in case of unexpected errors. Without @Transactional,
     * an exception might leave your database in an inconsistent state.
     *
     * 3) Integration with Other Transactional Operations (Future Proofing):
     * Your current method might only involve one data manipulation. However, there's
     * a good chance that the business logic could evolve in the future to include
     * additional data interactions within the same method. Applying @Transactional
     * from the beginning makes it easier to add more operations later without having
     * to revisit transaction management.
     *
     * 4) Read Isolation:
     * @Transactional helps manage the isolation level of your database operations.
     * Even for a single write operation, you might want to ensure a certain level
     * of isolation from concurrent read or write operations to maintain data
     * consistency. The default transaction isolation level might not always be
     * sufficient for your needs.
     *
     * 5) Consistency with Application-Wide Transaction Management:
     * If your application consistently uses @Transactional for all data-modifying
     * operations in the service layer, it promotes a uniform approach and reduces
     * the risk of forgetting transaction management in more complex scenarios.
     *
     * 6) Aspect-Oriented Programming (AOP) Benefits:
     * Spring's @Transactional is implemented using AOP. By using it consistently,
     * you benefit from Spring's declarative transaction management, which keeps
     * transaction management concerns separate from your core business logic.
     */
    @Transactional
    @Override
    public String updateCustomer(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = customerRepository.findById(customerDTO.getCustId()).orElseThrow(() -> new RuntimeException("Customer not found"));
        EntityUtils.mergeNonNullProperties(customerDTO, customerEntity);
        customerRepository.save(customerEntity);
//        saveImages(customerDTO);
        return "Customer updated successfully";
    }

    @Override
    public CustomerEntity getCustomerById(String id) {
        Long custId = Long.parseLong(id);
        return customerRepository.findById(custId).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public List<CustomerEntity> getAllCustomers(Integer page, Integer size, Boolean ascending) {
        if (size <= 0) {
            return customerRepository.findAll();
        }
//        Pageable pageable = Pageable.ofSize(size);
        Sort.Direction sorting = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorting, "cust_id"));
        return customerRepository.findLimitCustomer(pageable);
    }

    @Transactional
    @Override
    public String deleteCustomerById(String id) {
        Long custId = Long.parseLong(id);
        customerRepository.deleteById(custId);
        return "Deleted successfully";
    }

    @Override
    public Map<String, String> getPhoto(String id, String photoType) throws IOException {
        Long custId = Long.parseLong(id);
        CustomerEntity customerEntity = customerRepository.findById(custId).orElseThrow(() -> new RuntimeException("No customer found"));
        if (photoType.equals("customer")) {
            String customerPhoto = customerEntity.getCustomerPhoto();
            if (!customerPhoto.isEmpty()) {
                String name = photoType + "_" + customerPhoto;
                return Map.of(name, FileStorageUtil.getFile(id, name));
            }
            return Map.of("message", "No file found");
        } else if (photoType.equals("document")) {
            String docPhoto = customerEntity.getDocumentPhoto();
            if (!docPhoto.isEmpty()) {
                String name = photoType + "_" + docPhoto;
                return Map.of(name, FileStorageUtil.getFile(id, name));
            }
            return Map.of("message", "No file found");

        }
        return Map.of("message", "Invalid photo type");
    }

    private void saveImages(CustomerRegistrationDTO customerDTO, String custId) throws IOException {
        HashMap<String, String> custPhoto = customerDTO.getCustomerPhoto();
        String custfileName = custPhoto.keySet().stream().findFirst().orElse("");
        String custfileBase64 = custPhoto.values().stream().findFirst().orElse("");
        if (!custfileBase64.isEmpty()) {
            FileStorageUtil.saveBase64ToFile(custId, custfileBase64, "customer_" + custfileName);
        }
        HashMap<String, String> docPhoto = customerDTO.getDocumentPhoto();
        String docfileName = docPhoto.keySet().stream().findFirst().orElse("");
        String docfileBase64 = docPhoto.values().stream().findFirst().orElse("");
        if (!docfileBase64.isEmpty()) {
            FileStorageUtil.saveBase64ToFile(custId, docfileBase64, "document_" + docfileName);
        }
    }
}
