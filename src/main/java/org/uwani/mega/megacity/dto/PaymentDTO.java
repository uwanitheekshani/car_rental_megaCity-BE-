package org.uwani.mega.megacity.dto;

import java.sql.Timestamp;

public class PaymentDTO {
    private int id;
    private int bookingId;
    private int userId;
    private double paymentAmount;
    private String currency;
    private String paymentMethod;
    private Timestamp paymentDate;
    private String paymentStatus;
    private String transactionId;
    private String remarks;

    // Constructors
    public PaymentDTO() {}

    public PaymentDTO(int id, int bookingId, int userId, double paymentAmount, String currency, String paymentMethod,
                      Timestamp paymentDate, String paymentStatus, String transactionId, String remarks) {
        this.id = id;
        this.bookingId = bookingId;
        this.userId = userId;
        this.paymentAmount = paymentAmount;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
        this.remarks = remarks;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getPaymentAmount() { return paymentAmount; }
    public void setPaymentAmount(double paymentAmount) { this.paymentAmount = paymentAmount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public Timestamp getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Timestamp paymentDate) { this.paymentDate = paymentDate; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "id=" + id +
                ", bookingId=" + bookingId +
                ", userId=" + userId +
                ", paymentAmount=" + paymentAmount +
                ", currency='" + currency + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentDate=" + paymentDate +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
