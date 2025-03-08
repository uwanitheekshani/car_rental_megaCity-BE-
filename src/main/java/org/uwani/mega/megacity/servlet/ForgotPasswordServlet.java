package org.uwani.mega.megacity.servlet;

import org.uwani.mega.megacity.service.UserService;
import org.uwani.mega.megacity.service.impl.UserServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/password-reset")
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");  // Determines which action to perform
        String email = request.getParameter("email");
        String otpCode = request.getParameter("otpCode");
        String newPassword = request.getParameter("newPassword");

        System.out.println("Received Action: " + action);
        System.out.println("Received Email: " + email);
        System.out.println("OTP: " + otpCode);

        if (action == null || email == null || action.isEmpty() || email.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing required parameters: action or email");
            return;
        }
//        if (otpCode == null || otpCode.isEmpty()) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().write("OTP code is missing or invalid");
//            return;
//        }


        try {
            switch (action) {
                case "forgotPassword":
                    handleForgotPassword(email, response);
                    break;
                case "verifyOtp":
                    System.out.println("In Side verifyOTP");
                    handleVerifyOtp(email, otpCode, response);
                    break;
                case "changePassword":
                    handleChangePassword(email, newPassword, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    // 1️⃣ Forgot Password - Sends OTP to the user's email (Verify the email)
    private void handleForgotPassword(String email, HttpServletResponse response) throws IOException, MessagingException {
        String otpSent = userService.forgotPassword(email);
        if (otpSent!=null) {
            response.getWriter().write("OTP sent successfully.");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to send OTP.");
        }
    }

    // 2️⃣ Verify OTP - Checks if the OTP is correct
    private void handleVerifyOtp(String email, String otpCode, HttpServletResponse response) throws IOException {
        System.out.println("In Side Method");
        boolean otpValid = userService.verifyOtp(email, otpCode);
        if (otpValid) {
            response.getWriter().write("OTP verified successfully.");
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid OTP.");
        }
    }

    // 3️⃣ Change Password - Updates password after OTP verification
    private void handleChangePassword(String email, String newPassword, HttpServletResponse response) throws IOException {
        boolean passwordChanged = userService.changePassword(email, newPassword);
        if (passwordChanged) {
            response.getWriter().write("Password updated successfully.");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update password.");
        }
    }
}