package com.gym.app.mahesh_gym.service.impl;

import com.gym.app.mahesh_gym.entity.CustomerEntity;
import com.gym.app.mahesh_gym.entity.PaymentEntity;
import com.gym.app.mahesh_gym.repository.CustomerRepository;
import com.gym.app.mahesh_gym.repository.PaymentRepository;
import com.gym.app.mahesh_gym.service.PaymentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;


    public PaymentServiceImpl(PaymentRepository paymentRepository, CustomerRepository customerRepository) {
        this.paymentRepository = paymentRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public String paymentRequest(PaymentEntity paymentEntity) {
        LocalDate toDate = paymentEntity.getToDate();
        if (toDate == null) {
            LocalDate fromDate = paymentEntity.getFromDate();
            paymentEntity.setToDate(fromDate.plusMonths(1));
        }
        Optional<PaymentEntity> latestPayment = paymentRepository.findTopByCustIdOrderByToDateDesc(paymentEntity.getCustId());
        if (latestPayment.isPresent() &&
                (latestPayment.get().getToDate().isEqual(paymentEntity.getFromDate())
                        || latestPayment.get().getToDate().isAfter(paymentEntity.getFromDate()))) {
            return "Payment already paid till date " + latestPayment.get().getToDate() + ", Please select date after " + latestPayment.get().getToDate();
        } else if (paymentEntity.getFromDate().isAfter(paymentEntity.getToDate())) {
            return "From Date should be greater than To Date";
        }
        paymentRepository.save(paymentEntity);
        Optional<CustomerEntity> customerEntity = customerRepository.findById(paymentEntity.getCustId());
        if (customerEntity.isPresent()) {
            CustomerEntity customerEntity1 = customerEntity.get();
            customerEntity1.setCurrentMonthFeePaid(false);
            Period period = Period.between(paymentEntity.getFromDate(), paymentEntity.getToDate());
            int monthsDifference = (period.getMonths() + period.getYears() * 12) + 1;
            LocalDate currentDate = LocalDate.now();
            if ((paymentEntity.getFromDate().isBefore(currentDate)
                    || paymentEntity.getFromDate().isEqual(currentDate))
                    &&
                    (paymentEntity.getToDate().isEqual(currentDate)
                            || currentDate.isBefore(paymentEntity.getToDate()))) {
                customerEntity1.setCurrentMonthFeePaid(true);
            }
            customerEntity1.setAmountPaid(customerEntity1.getMonthlyAmountPaid() * monthsDifference);
            customerRepository.save(customerEntity1);
        }
        return "Payment successful";
    }

    @Override
    public List<PaymentEntity> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public List<PaymentEntity> getAllPaymentsByCustId(String id) {
        return paymentRepository.findAllByCustIdOrderByFromDateDesc(Long.parseLong(id));
    }

    @Override
    public List<PaymentEntity> getPaymentMonthlyHistory(LocalDate date) {
        List<String> custIds = paymentRepository.findAllCustIdContainingGivenDate(date);
        return new ArrayList<>();
    }
}
