package org.uwani.mega.megacity.service.impl;



import org.uwani.mega.megacity.dao.PaymentDAO;
import org.uwani.mega.megacity.dao.PaymentDAOImpl;
import org.uwani.mega.megacity.entity.Payment;
import org.uwani.mega.megacity.service.PaymentService;

import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    private PaymentDAO paymentDAO = new PaymentDAOImpl();

    @Override
    public void processPayment(Payment payment) {
        if (payment.getPaymentAmount() <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero.");
        }
        paymentDAO.createPayment(payment);
    }

    @Override
    public Payment getPaymentById(int id) {
        return paymentDAO.getPaymentById(id);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentDAO.getAllPayments();
    }

    @Override
    public void updatePaymentStatus(int paymentId, String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Payment status cannot be null or empty.");
        }

        Payment payment = paymentDAO.getPaymentById(paymentId);
        if (payment != null) {
            payment.setPaymentStatus(status);
            paymentDAO.updatePayment(payment);
        } else {
            throw new IllegalArgumentException("Payment with ID " + paymentId + " not found.");
        }
    }


    @Override
    public void deletePayment(int id) {
        paymentDAO.deletePayment(id);
    }
}
