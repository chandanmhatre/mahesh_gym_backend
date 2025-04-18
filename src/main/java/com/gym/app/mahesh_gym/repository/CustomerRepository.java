package com.gym.app.mahesh_gym.repository;

import com.gym.app.mahesh_gym.entity.CustomerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

//    @Query("SELECT cust FROM CustomerEntity cust ORDER BY cust.custId DESC LIMIT 1")
//    CustomerEntity findLastCustomer();


    @Query("SELECT cust FROM CustomerEntity cust")
//  @Query("SELECT cust FROM CustomerEntity cust ORDER BY cust.cust_id DESC")
//  not have dynamic sorting option in query, but have in Pageable
    List<CustomerEntity> findLimitCustomer(Pageable pageable);

    // want to use native query
//    @Query(value = "SELECT * FROM customer ORDER BY
//               CASE WHEN :sortDirection = 'ASC' THEN cust_id END ASC,
//               CASE WHEN :sortDirection = 'DESC' THEN cust_id END DESC
//               LIMIT :limit",
//       nativeQuery = true)
//    (Issues with This Query
//      MySQL does not allow dynamic ordering using CASE like this.
//      The CASE condition evaluates to NULL for the non-matching row, leading to incorrect results.
//      The correct way to dynamically sort is not using CASE, but instead using dynamic string concatenation or native query execution.)
//    List<CustomerEntity> findLimitCustomer(@Param("limit") int limit, @Param("sortDirection") String sortDirection);
//
//    String sortDirection = "DESC";  // or "ASC"
//    String query = "SELECT * FROM customer ORDER BY cust_id " + sortDirection + " LIMIT :limit";
//    Query nativeQuery = entityManager.createNativeQuery(query, CustomerEntity.class);
//    nativeQuery.setParameter("limit", 10);
//    List<CustomerEntity> customers = nativeQuery.getResultList();
//    need this to call directly in method


    //    Filters customers based on whether the subquery returns any rows. A customer is included if the subquery returns no rows.
    @Query("SELECT c FROM CustomerEntity c WHERE EXISTS (" + // check if in DB customer exist
            "SELECT 1 FROM PaymentEntity p " +
            "WHERE p.custId = c.custId " +
            "AND CURRENT_DATE NOT BETWEEN p.fromDate AND p.toDate " + // where current date is not exist in paid from date and to date
            "AND c.currentMonthFeePaid = true)" // and also check if there marked as paid (1)
    )
    List<CustomerEntity> findCustomersWithNoActivePaymentOnCurrentDate();
}
