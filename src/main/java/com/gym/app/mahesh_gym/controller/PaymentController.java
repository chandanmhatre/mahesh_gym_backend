package com.gym.app.mahesh_gym.controller;


import com.gym.app.mahesh_gym.entity.PaymentEntity;
import com.gym.app.mahesh_gym.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment_request")
    public String paymentRequest(@RequestBody PaymentEntity paymentEntity) {
        return paymentService.paymentRequest(paymentEntity);
    }

    @GetMapping("/get_all_payments")
    public List<PaymentEntity> getAllPayments(@RequestParam(name = "id", defaultValue = "") String id) {
        if (id.isEmpty()) {
            return paymentService.getAllPayments();
        }
        return paymentService.getAllPaymentsByCustId(id);
    }

    @GetMapping("/payment_monthly_history")
    public List<PaymentEntity> getPaymentMonthlyHistory(@RequestParam LocalDate date) {
        return paymentService.getPaymentMonthlyHistory(date);
    }
}
