package org.uwani.mega.megacity.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.uwani.mega.megacity.service.impl.PasswordResetServiceImpl;

import java.io.IOException;

@WebServlet("/reset-password")
public class PasswordResetServlet extends HttpServlet {

    private final PasswordResetServiceImpl passwordResetService = new PasswordResetServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // Get the token from the Authorization header
            String authHeader = request.getHeader("Authorization");



            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\":\"Token is required.\"}");
                return;
            }

            // Extract the token by removing "Bearer " prefix
            String token = authHeader.substring(7);

            // Get new password from request body or parameters
            String newPassword = request.getParameter("newPassword");

            System.out.println("Authorization Header: " + authHeader);
            System.out.println("New Password: " + newPassword);
            if (newPassword == null || newPassword.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\":\"New password is required.\"}");
                return;
            }

            // Call the resetPassword service method
            boolean resetSuccessful = passwordResetService.resetPassword(token, newPassword);

            if (resetSuccessful) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\":\"Password reset successful\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\":\"Failed to reset password. Invalid token or user.\"}");
            }
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"Invalid or expired token.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"An error occurred\"}");
        }
    }
}
