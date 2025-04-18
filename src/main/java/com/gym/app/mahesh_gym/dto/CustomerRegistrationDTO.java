package com.gym.app.mahesh_gym.dto;

import jakarta.validation.constraints.*;
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
//  Data Transfer Objects
public class CustomerRegistrationDTO {

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Address cannot be null")
    @NotBlank(message = "Address cannot be blank")
    @Max(value = 255, message = "Address cannot exceed 255 characters")
    private String address;

    @NotNull(message = "Mobile number cannot be null")
    @NotBlank(message = "Mobile number cannot be blank")
    @Size(min = 10, max = 15, message = "Mobile number should be 10 to 15 digit long")
    @Pattern(regexp = "^[0-9]*$")
    private String mobile;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Must be a valid email address")
    private String email;

    @NotNull(message = "Rule start date cannot be null")
    @NotBlank(message = "Rule start date cannot be blank")
//    @Past(message = "Date must be in the past")
//    @Future(message = "Date must be in the future")
//    @PastOrPresent(message = "Date must be in the past or present")
//    @FutureOrPresent(message = "Date must be in the future or present")
//    Below validation using custom validator
//    @DateRange(min = "2023-01-01", max = "2025-12-31", message = "Date must be between 2023-01-01 and 2025-12-31")
    private LocalDate ruleStartDate;

    @NotBlank(message = "Billing day cannot be blank")
    @Min(value = 1, message = "Billing day must be at least 1")
    @Max(value = 31, message = "Billing day must be at most 31")
    private Integer billingDay;

    @NotNull(message = "Billing day cannot be null")
    @NotBlank(message = "Billing day cannot be blank")
    @DecimalMin(value = "0.00", message = "Amount paid must be at least 0.00")
    private Double amountPaid;

    @NotNull(message = "Monthly amount paid cannot be null")
    @NotNull(message = "Monthly amount paid cannot be null")
    @DecimalMin(value = "0.00", message = "Monthly amount paid must be at least 0.00")
    private Double monthlyAmountPaid;

    private Boolean smsStatus;

    @Size(max = 500, message = "Additional note cannot exceed 500 characters")
    private String additionalNote;

    private Boolean currentMonthFeePaid;

    private HashMap<String, String> customerPhoto;

    private HashMap<String, String> documentPhoto;
}
