package org.uwani.mega.megacity.service.impl;



import org.uwani.mega.megacity.dao.PaymentDAO;
import org.uwani.mega.megacity.dao.PaymentDAOImpl;
import org.uwani.mega.megacity.dto.BookingDTO;
import org.uwani.mega.megacity.dto.CarDTO;
import org.uwani.mega.megacity.entity.Booking;
import org.uwani.mega.megacity.entity.Payment;
import org.uwani.mega.megacity.service.BookingService;
import org.uwani.mega.megacity.service.CarService;
import org.uwani.mega.megacity.service.PaymentService;

import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    private PaymentDAO paymentDAO = new PaymentDAOImpl();
    private CarService carService = new CarServiceImpl();
    private BookingService bookingService = new BookingServiceImpl();
    private InvoiceService invoiceService = new InvoiceService();
//    @Override
//    public void processPayment(Payment payment) {
//        if (payment.getPaymentAmount() <= 0) {
//            throw new IllegalArgumentException("Payment amount must be greater than zero.");
//        }
//        paymentDAO.createPayment(payment);
//    }

//    @Override
//    public void processPayment(Payment payment) {
//        if (payment.getPaymentAmount() <= 0) {
//            throw new IllegalArgumentException("Payment amount must be greater than zero.");
//        }
//
//        boolean ispayed = paymentDAO.createPayment(payment);
//        if(ispayed){
//            Booking booking =bookingService.getBookingById(payment.getBookingId());
//            BookingDTO bookingDTO = new BookingDTO();
//            bookingDTO.setStatus("Completed");
//            bookingDTO.setTotalAmount(booking.getTotalAmount());
//            bookingDTO.setStartDate(booking.getStartDate().toString());
//            bookingDTO.setEndDate(booking.getEndDate().toString());
//            bookingService.updateBooking(booking.getId(),bookingDTO);
//            CarDTO carDTO =carService.getCarById(booking.getCarId());
//            carDTO.setStatus("Available");
//            carService.updateCar(carDTO);
//        }
//    }

    @Override
    public void processPayment(Payment payment) {
        if (payment.getPaymentAmount() <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero.");
        }

//        boolean isPaid = paymentDAO.createPayment(payment);
        Payment savedPayment = paymentDAO.createPaymentV2(payment);
        if (savedPayment!=null) {
            Booking booking = bookingService.getBookingById(payment.getBookingId());
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setStatus("Completed");
            bookingDTO.setTotalAmount(booking.getTotalAmount());
            bookingDTO.setStartDate(booking.getStartDate().toString());
            bookingDTO.setEndDate(booking.getEndDate().toString());
            bookingService.updateBooking(booking.getId(), bookingDTO);

            CarDTO carDTO = carService.getCarById(booking.getCarId());
            carDTO.setStatus("Available");
            carService.updateCar(carDTO);

            // Generate invoice after successful payment
            try {
                invoiceService.generateInvoice(savedPayment, booking);
            } catch (Exception e) {
                throw new RuntimeException("Invoice generation failed: " + e.getMessage());
            }
        }
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
