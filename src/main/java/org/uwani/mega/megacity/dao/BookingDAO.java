package org.uwani.mega.megacity.dao;


import org.uwani.mega.megacity.config.DBConfig;
import org.uwani.mega.megacity.entity.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO  {
    // CREATE Booking
    public boolean createBooking(Booking booking) {
        String sql = "INSERT INTO bookings (user_id, car_id, start_date, end_date, total_amount, status, driver_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getCarId());
            stmt.setDate(3, new java.sql.Date(booking.getStartDate().getTime()));
            stmt.setDate(4, new java.sql.Date(booking.getEndDate().getTime()));
            stmt.setDouble(5, booking.getTotalAmount());
            stmt.setString(6, booking.getStatus());
            stmt.setInt(7, booking.getDriverId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ Booking by ID
    public Booking getBookingById(int id) {
        String sql = "SELECT * FROM bookings WHERE id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("car_id"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getDouble("total_amount"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE Booking
    public boolean updateBooking(Booking booking) {
        String sql = "UPDATE bookings SET start_date = ?, end_date = ?, total_amount = ?, status = ? WHERE id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(booking.getStartDate().getTime()));
            stmt.setDate(2, new java.sql.Date(booking.getEndDate().getTime()));
            stmt.setDouble(3, booking.getTotalAmount());
            stmt.setString(4, booking.getStatus());
            stmt.setInt(5, booking.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE Booking
    public boolean deleteBooking(int id) {
        String sql = "DELETE FROM bookings WHERE id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // LIST All Bookings
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("car_id"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getDouble("total_amount"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getInt("driver_id")

                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public List<Booking> getAllBookingsById(int user_id) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE driver_id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user_id); // ✅ Correctly placed before execution

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bookings.add(new Booking(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("car_id"),
                            rs.getDate("start_date"),  // ✅ Convert to LocalDate
                            rs.getDate("end_date"),    // ✅ Convert to LocalDate
                            rs.getDouble("total_amount"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at"), // ✅ Convert to LocalDateTime
                            rs.getTimestamp("updated_at")  // ✅ Convert to LocalDateTime
                    ));
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error fetching bookings for user ID: " + user_id);
            e.printStackTrace();
        }

        return bookings;
    }

}
