package org.uwani.mega.megacity.service;

public interface PasswordResetService {

    boolean generateResetToken(String email);  // To generate reset token and send the email

    boolean resetPassword(String token, String newPassword);  // To reset the password based on the token
}
