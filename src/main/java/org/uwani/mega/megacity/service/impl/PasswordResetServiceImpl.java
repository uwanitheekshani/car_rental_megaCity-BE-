package org.uwani.mega.megacity.service.impl;



import org.uwani.mega.megacity.dao.UserDAO;
import org.uwani.mega.megacity.entity.User;
import org.uwani.mega.megacity.filters.JwtUtil;
import org.uwani.mega.megacity.service.PasswordResetService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserDAO userDAO = new UserDAO();

    @Override
    public boolean generateResetToken(String email) {
        // Find the user by email
        User user = userDAO.findByEmail(email);
        if (user == null) {
            return false;  // User not found
        }

        // Generate a reset token (JWT)
        String resetToken = JwtUtil.generateToken(user.getUsername(), "reset");
        System.out.println("Reset token generated: " + resetToken);  // Log token to check

        // Send email with reset token
        sendResetEmail(email, resetToken);

        return true;
    }

    public void sendResetEmail(String email, String resetToken) {
        String from = "no-reply@megacity.com";  // Replace with your sender email
        String password = "your-email-password"; // Replace with your email password

        String subject = "Password Reset Request";
        String resetLink = "http://localhost:8080/MegaCity_war_exploded/reset-password?token=" + resetToken;
        String body = "To reset your password, click the link below:\n" + resetLink;

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");  // Port for TLS
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS

        // Session setup with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Create the email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);
            System.out.println("Password reset email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email. Error: " + e.getMessage());
        }
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        try {
            // Verify the token and extract username from it
            String username = JwtUtil.verifyToken(token).getSubject();

            // Find the user by username
            User user = userDAO.findByEmail(username);
            if (user == null) {
                return false;
            }

            // Update the password
            return userDAO.updatePassword(user.getEmail(), newPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
