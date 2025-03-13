package org.uwani.mega.megacity.service.impl;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.uwani.mega.megacity.dto.CarDTO;
import org.uwani.mega.megacity.entity.Booking;
import org.uwani.mega.megacity.entity.Payment;
import org.uwani.mega.megacity.entity.User;
import org.uwani.mega.megacity.service.CarService;
import org.uwani.mega.megacity.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class InvoiceService {

    // Directory for saving invoices
    private static final String INVOICE_DIRECTORY = System.getProperty("user.dir") + "/invoices/";

    private final UserService userService = new UserServiceImpl();
    private final CarService carService = new CarServiceImpl();

    public String generateInvoice(Payment payment, Booking booking) {
        // Define invoice filename
        String filePath = INVOICE_DIRECTORY + "invoice_" + payment.getId() + ".pdf";

        try {
            // Ensure the directory exists
            Files.createDirectories(Paths.get(INVOICE_DIRECTORY));

            // Load JasperReport template
            try (InputStream jasperStream = getClass().getResourceAsStream("/reports/PaymentBill.jasper")) {
                if (jasperStream == null) {
                    throw new FileNotFoundException("Jasper file not found: /reports/PaymentBill.jasper");
                }

                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

                // Fetch necessary data for the invoice
                User user = userService.getUserById(payment.getUserId());
                CarDTO car = carService.getCarById(booking.getCarId());

                // Set parameters for the JasperReport
                Map<String, Object> parameters = new HashMap<>();
                System.out.println("paymentNo: "+payment.getId());
                parameters.put("paymentNo", payment.getId());
                parameters.put("model", car.getName());
                parameters.put("plate_number", car.getPlate_number());
                parameters.put("name", user.getUsername());
                parameters.put("totalAmount", payment.getPaymentAmount());
                parameters.put("date", payment.getPaymentDate());
                parameters.put("startDate", booking.getStartDate());
                parameters.put("endDate", booking.getEndDate());

                // Fill the report
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

                // Save the PDF
                try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                    JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                }

                // Verify if the file was saved successfully
                File invoiceFile = new File(filePath);
                if (!invoiceFile.exists()) {
                    throw new IOException("Failed to save the invoice PDF.");
                }

                System.out.println("Invoice successfully saved at: " + filePath);

                // Return the URL for frontend access
                return "http://yourserver.com/invoices/invoice_" + payment.getId() + ".pdf";

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating invoice: " + e.getMessage();
        }
    }
}