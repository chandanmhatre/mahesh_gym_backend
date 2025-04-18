package com.gym.app.mahesh_gym.repository;


import com.gym.app.mahesh_gym.entity.CustomerEntity;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerPaymentResetService {
    private final CustomerRepository customerRepository;

    public CustomerPaymentResetService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Transactional
    private void resetPaymentStatusForCustomersWithoutActivePayment() {
        System.out.println("Starting process to reset payment status for customers without active payments on the current date.");
        List<CustomerEntity> customersToReset = customerRepository.findCustomersWithNoActivePaymentOnCurrentDate();
        if (customersToReset.isEmpty()) {
            System.out.println("No customers found without active payments on the current date. Process complete.");
            return;
        }

        customersToReset.forEach((CustomerEntity c) -> {
            c.setAmountPaid(0.0);
            c.setCurrentMonthFeePaid(false);
            customerRepository.save(c);
            System.out.println("Successfully reset payment status for customer with ID: " + c.getCustId());
        });

        System.out.println("Successfully reset payment status for all " + customersToReset.size() + " identified customers.");
    }


    //    fixedDelay:
    //    Executes the annotated method after a fixed delay in milliseconds after the previous execution completes.
    //    If the previous task takes longer than the fixedDelay, the next execution will start immediately after the previous one finishes.
    //    @Scheduled(fixedDelay = 5000) // Execute every 5 seconds after completion

    //    fixedRate:
    //    Executes the annotated method at a fixed rate in milliseconds. The execution starts at a fixed interval, regardless of how long the previous execution took.
    //    If a previous execution takes longer than the fixedRate, the next execution will start immediately, potentially leading to overlapping executions.
    @Scheduled(fixedRate = 10000) // Execute every 10 seconds

    //    initialDelay:
    //    Used in conjunction with fixedDelay or fixedRate. It specifies the delay in milliseconds before the first execution of the task.
    //    Subsequent executions follow the fixedDelay or fixedRate.
    //    @Scheduled(fixedRate = 3000, initialDelay = 10000) // First execution after 10 seconds, then every 3 seconds

    //    Provides more sophisticated scheduling using cron expressions. Cron is a standard Unix utility for scheduling commands to run periodically at certain times or dates.
    //    cron = "0 0 12 * * ?" is a cron expression that defines the schedule:
    //    0: Seconds (0-59)
    //    0: Minutes (0-59)
    //    12: Hours (0-23)
    //    *: Day of month (1-31)
    //    *: Month (1-12 or JAN-DEC)
    //    ?: Day of week (1-7 or SUN-SAT). ? means "no specific value."
    //    (optional) year
    //    @Scheduled(cron = "0 0 * * * *") // Execute at the beginning of every hour
    //    @Scheduled(cron = "0 30 9 * * MON-FRI") // Execute at 9:30 AM on weekdays
    //    @Scheduled(cron = "0 30 20 * * MON-FRI") // Execute at 10:30 PM on weekdays
    //    @Scheduled(cron = "0 0 0 * * ?") // run 12:00 AM everyday

    //    zone:
    //    Specifies the time zone to be used for cron expressions. By default, the server's local time zone is used.
    //    You can specify a java.util.TimeZone ID.
    //    @Scheduled(cron = "0 0 10 * * *", zone = "America/New_York") // Execute at 10:00 AM New York time

    public void reportCurrentTime() {
        System.out.println("The time is now {}" + LocalDate.now());
        resetPaymentStatusForCustomersWithoutActivePayment();
    }
}
