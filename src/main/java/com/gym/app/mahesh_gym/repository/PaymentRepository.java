package com.gym.app.mahesh_gym.repository;


import com.gym.app.mahesh_gym.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    Optional<PaymentEntity> findTopByCustIdOrderByToDateDesc(Long id);
//    Spring Data JPA automatically generates the SQL query based on the method name. This eliminates the need to write manual SQL queries.
//    important considerations:
//    Ensure that your PaymentEntity class has a custId field and a toDate field.
//    Ensure that your database table has corresponding columns.
//    The method name convention is crucial for Spring Data JPA to work correctly.

    List<PaymentEntity> findAllByCustIdOrderByFromDateDesc(Long id);


    @Query("SELECT p.custId FROM PaymentEntity p WHERE :date BETWEEN p.fromDate AND p.toDate")
    List<String> findAllCustIdContainingGivenDate(LocalDate date);

}
