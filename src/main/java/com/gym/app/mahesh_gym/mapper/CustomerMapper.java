package com.gym.app.mahesh_gym.mapper;

import com.gym.app.mahesh_gym.dto.CustomerDTO;
import com.gym.app.mahesh_gym.dto.CustomerRegistrationDTO;
import com.gym.app.mahesh_gym.entity.CustomerEntity;

import java.util.HashMap;
import java.util.Map;

public class CustomerMapper {
    public static CustomerEntity toEntity(CustomerDTO customerDTO) {
//        Why is smsStatus becoming null if customer DTO smsStatus=null?
//                When mapping from DTO to Entity:
//
//        If smsStatus is null in CustomerDTO, it is explicitly passed as null to CustomerEntity, overriding the default value (true).
//
//                Why doesn't the default value (true) work?
//
//        Default values in an entity (private Boolean smsStatus = true;) are only applied when JPA initializes the entity (e.g., when creating a new object without explicitly setting values).
//
//        When you explicitly set null via the constructor, it overrides the default value.
        return new CustomerEntity(customerDTO.getCustId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getMobile(), customerDTO.getEmail(), customerDTO.getRuleStartDate(), customerDTO.getBillingDay(), customerDTO.getAmountPaid(), customerDTO.getMonthlyAmountPaid(), customerDTO.getSmsStatus(), customerDTO.getAdditionalNote(), customerDTO.getCurrentMonthFeePaid(), String.valueOf(customerDTO.getCustomerPhoto().keySet().stream().findFirst().orElse("")), String.valueOf(customerDTO.getDocumentPhoto().keySet().stream().findFirst().orElse("")));
    }

    public static CustomerEntity toEntity(CustomerRegistrationDTO customerDTO) {
        return new CustomerEntity(null, customerDTO.getName(), customerDTO.getAddress(), customerDTO.getMobile(), customerDTO.getEmail(), customerDTO.getRuleStartDate(), customerDTO.getBillingDay(), customerDTO.getAmountPaid(), customerDTO.getMonthlyAmountPaid(), customerDTO.getSmsStatus(), customerDTO.getAdditionalNote(), customerDTO.getCurrentMonthFeePaid(), String.valueOf(customerDTO.getCustomerPhoto().keySet().stream().findFirst().orElse("")), String.valueOf(customerDTO.getDocumentPhoto().keySet().stream().findFirst().orElse("")));
    }

    public static CustomerDTO toDTO(CustomerEntity customerEntity, String customerBase64, String docBase64) {
        return new CustomerDTO(customerEntity.getCustId(), customerEntity.getName(), customerEntity.getAddress(), customerEntity.getMobile(), customerEntity.getEmail(), customerEntity.getRuleStartDate(), customerEntity.getBillingDay(), customerEntity.getAmountPaid(), customerEntity.getMonthlyAmountPaid(), customerEntity.getSmsStatus(), customerEntity.getAdditionalNote(), customerEntity.getCurrentMonthFeePaid(), new HashMap<>(Map.of(customerEntity.getCustomerPhoto(), customerBase64)), new HashMap<>(Map.of(customerEntity.getDocumentPhoto(), docBase64)));
    }
}
