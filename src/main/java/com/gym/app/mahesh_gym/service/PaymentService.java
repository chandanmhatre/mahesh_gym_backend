package com.gym.app.mahesh_gym.service;

import com.gym.app.mahesh_gym.entity.PaymentEntity;

import java.time.LocalDate;
import java.util.List;

public interface PaymentService {
    String paymentRequest(PaymentEntity paymentEntity);

    List<PaymentEntity> getAllPayments();

    List<PaymentEntity> getAllPaymentsByCustId(String id);

    List<PaymentEntity> getPaymentMonthlyHistory(LocalDate date);
}
