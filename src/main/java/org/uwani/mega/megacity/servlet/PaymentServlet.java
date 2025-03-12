package org.uwani.mega.megacity.servlet;



import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.uwani.mega.megacity.entity.Payment;
import org.uwani.mega.megacity.service.PaymentService;
import org.uwani.mega.megacity.service.impl.PaymentServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/payment/*")
public class PaymentServlet extends HttpServlet {
    private PaymentService paymentService = new PaymentServiceImpl();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        try {
            // Read the JSON payload from the request
            BufferedReader reader = request.getReader();
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            // Parse the JSON into a Payment object
            String jsonData = jsonBuilder.toString();
            Payment payment = gson.fromJson(jsonData, Payment.class);

            // Validate and ensure payment status is one of the allowed values
            if (payment.getPaymentStatus() == null || payment.getPaymentStatus().isEmpty()) {
                payment.setPaymentStatus("pending"); // Default status if not provided
            }

            // Call the service to process payment
            paymentService.processPayment(payment);

            response.getWriter().write(gson.toJson("Payment processed successfully!"));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson("Error processing payment: " + e.getMessage()));
        }
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("application/json");
//
//        String paymentId = request.getParameter("id");
//        if (paymentId != null) {
//            try {
//                int id = Integer.parseInt(paymentId);
//                Payment payment = paymentService.getPaymentById(id);
//                response.getWriter().write(gson.toJson(payment));
//            } catch (NumberFormatException e) {
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                response.getWriter().write(gson.toJson("Invalid payment ID"));
//            }
//        } else {
//            List<Payment> payments = paymentService.getAllPayments();
//            response.getWriter().write(gson.toJson(payments));
//        }
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        // Retrieve the action parameter
        String action = request.getParameter("action");

        if ("getById".equals(action)) {
            String paymentId = request.getParameter("id");
            if (paymentId != null) {
                try {
                    int id = Integer.parseInt(paymentId);
                    Payment payment = paymentService.getPaymentById(id);
                    response.getWriter().write(gson.toJson(payment));
                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write(gson.toJson("Invalid payment ID"));
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(gson.toJson("Payment ID is required"));
            }
        } else if ("getAll".equals(action)) {
            List<Payment> payments = paymentService.getAllPayments();
            response.getWriter().write(gson.toJson(payments));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson("Invalid action"));
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Read 'id' from request parameters
            String idParam = request.getParameter("id");

            // Read the JSON body (payment status)
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                requestBody.append(line);
            }

            // Parse the body to get the payment status (since the body is in JSON format)
            String status = null;
            if (requestBody.length() > 0) {
                // We are expecting the body to be a JSON like {"paymentStatus": "completed"}
                String json = requestBody.toString();
                if (json.contains("paymentStatus")) {
                    // Extract the value from the JSON body
                    status = json.split(":")[1].replaceAll("[\"{}]", "").trim();
                }
            }

            // Ensure the 'id' parameter is not null and is a valid integer
            if (idParam == null || idParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Payment ID is required.");
                return;
            }

            int paymentId = Integer.parseInt(idParam); // parse ID to integer

            // Fetch the existing payment from the database
            Payment payment = paymentService.getPaymentById(paymentId);

            if (payment == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Payment not found.");
                return;
            }

            // If 'status' is null or empty, return an error response
            if (status == null || status.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Payment status cannot be null or empty.");
                return;
            }

            // Call service to update payment status
            paymentService.updatePaymentStatus(paymentId, status);

            // Send success response
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Payment status updated successfully.");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred: " + e.getMessage());
            e.printStackTrace();  // For debugging purposes
        }
    }




    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        try {
            int paymentId = Integer.parseInt(request.getParameter("id"));
            paymentService.deletePayment(paymentId);
            response.getWriter().write(gson.toJson("Payment deleted successfully!"));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson("Error deleting payment: " + e.getMessage()));
        }
    }
}
