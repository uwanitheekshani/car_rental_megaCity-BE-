package org.uwani.mega.megacity.dao;



import org.uwani.mega.megacity.entity.Payment;

import java.util.List;

public interface PaymentDAO {
    boolean createPayment(Payment payment);
    Payment getPaymentById(int id);
    List<Payment> getAllPayments();
    void updatePayment(Payment payment);
    void deletePayment(int id);
    Payment createPaymentV2(Payment payment);
}
