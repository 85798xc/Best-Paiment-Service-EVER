package org.example.paymentderviceaplicationii.repository;

import org.example.paymentderviceaplicationii.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    List<PaymentTransaction> findAllByUserId(Long userId);
}
