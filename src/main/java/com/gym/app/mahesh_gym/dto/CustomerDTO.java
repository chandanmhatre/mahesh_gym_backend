package com.gym.app.mahesh_gym.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private String name;
    private String address;
    private String mobile;
    private String email;
    private LocalDate ruleStartDate;
    private Integer billingDay;
    private Double amountPaid;
    private Double monthlyAmountPaid;
    private Boolean smsStatus;
    private String additionalNote;
    private Boolean currentMonthFeePaid;
    private HashMap<String, String> customerPhoto;
    private HashMap<String, String> documentPhoto;
}
