package com.gym.app.mahesh_gym.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 15)
    private String mobile;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDate ruleStartDate = LocalDate.now();

    @Column(nullable = false, length = 2)
    private Integer billingDay = LocalDate.now().getDayOfMonth();

    @Column(nullable = false)
    private Double amountPaid;

    @Column(nullable = false)
    private Double monthlyAmountPaid;

    @Column(nullable = false)
    private Boolean smsStatus;  // No need for default value here

    @Column()
    private String additionalNote;

    @Column(nullable = false)
    private Boolean currentMonthFeePaid = false;

    @Column()
    private String customerPhoto;

    @Column()
    private String documentPhoto;

    @PrePersist
    public void prePersist() {
        if (smsStatus == null) {
            smsStatus = true; // Set default only if it's null
        }
    }
}
