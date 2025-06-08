package org.example.paymentderviceaplicationii.repository;

import org.example.paymentderviceaplicationii.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {

}
