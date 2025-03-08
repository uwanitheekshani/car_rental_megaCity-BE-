package org.uwani.mega.megacity.dao;

import org.mindrot.jbcrypt.BCrypt;
import org.uwani.mega.megacity.config.DBConfig;
import org.uwani.mega.megacity.entity.ProfileImage;
import org.uwani.mega.megacity.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Save user with hashed password
    public boolean save(User user) {
        String sql = "INSERT INTO user (username, password, email, phone, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Hash the password before saving
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            stmt.setString(1, user.getUsername());
            stmt.setString(2, hashedPassword);  // Save hashed password
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getRole());
            System.out.println(stmt);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Find user by username
    // Find user by email
    public User findByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        System.out.println("sql: "+sql);
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Validate password using BCrypt
    public boolean checkPassword(User user, String password) {
        System.out.println("checkPassword BCrypt "+user.getPassword());
        return BCrypt.checkpw(password, user.getPassword()); // Verifies password against the hashed one
    }

    // Add method to update the password in the database
    public boolean updatePassword(String email, String newPassword) {
        String sql = "UPDATE user SET password = ? WHERE email = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Hash the new password before saving
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            stmt.setString(1, hashedPassword);  // Update password
            stmt.setString(2, email);  // Set email where the password should be updated

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public ProfileImage getProfileImage(String email) {
        String query = "SELECT image_id, user_email, image_path, upload_date FROM user_profile_images "
                + "WHERE user_email = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);  // Set user email
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ProfileImage profileImage = new ProfileImage();
                profileImage.setImageId(rs.getInt("image_id"));
                profileImage.setUserEmail(rs.getString("user_email"));
                profileImage.setImagePath(rs.getString("image_path"));
                profileImage.setUploadDate(rs.getString("upload_date"));
                return profileImage;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if no image is found
    }


    public boolean insertProfileImage(String email, String imagePath) {
        String query = "INSERT INTO user_profile_images (user_email, image_path) "
                + "SELECT email, ? FROM users WHERE email = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, imagePath);  // Set image path
            stmt.setString(2, email);  // Set user email
            return stmt.executeUpdate() > 0;  // Return true if insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




// for booking
    public User getRandomDriver() throws SQLException {
        String sql = "SELECT * FROM user WHERE role = 'driver' ORDER BY RAND() LIMIT 1";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                return user;
            }
        }
        return null;
    }
    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM user WHERE id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                return user;
            }
        }
        return null;
    }
}
