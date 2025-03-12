package org.uwani.mega.megacity.dao;


import org.uwani.mega.megacity.config.DBConfig;
import org.uwani.mega.megacity.entity.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarUserFilteringDAO {
    private static final String GET_ALL_CARS_SQL = "SELECT id, name, model, plate_number, year, status FROM car";
    private static final String GET_CAR_BY_ID_SQL = "SELECT id, name, model, plate_number, year, status FROM car WHERE id = ?";
    private static final String FILTER_CARS_BY_STATUS_SQL = "SELECT id, name, model, plate_number, year, status FROM car WHERE status = ?";
    private static final String FILTER_CARS_BY_MODEL_SQL = "SELECT id, name, model, plate_number, year, status FROM car WHERE model = ?";
    private static final String FILTER_CARS_BY_STATUS_AND_MODEL_SQL = "SELECT id, name, model, plate_number, year, status FROM car WHERE status = ? AND model = ?"; // New query
    // Fetch all cars
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_CARS_SQL)) {

            while (rs.next()) {
                cars.add(new Car(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("model"),
                        rs.getString("plate_number"),
                        rs.getInt("year"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error properly in production
        }
        return cars;
    }

    // Fetch car by ID
    public Car getCarById(int id) {
        Car car = null;
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_CAR_BY_ID_SQL)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    car = new Car(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("model"),
                            rs.getString("plate_number"),
                            rs.getInt("year"),
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    // Filter cars by status
    public List<Car> filterCarsByStatus(String status) {
        List<Car> cars = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FILTER_CARS_BY_STATUS_SQL)) {

            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cars.add(new Car(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("model"),
                            rs.getString("plate_number"),
                            rs.getInt("year"),
                            rs.getString("status")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    // Filter cars by model
    public List<Car> filterCarsByModel(String model) {
        List<Car> cars = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FILTER_CARS_BY_MODEL_SQL)) {

            stmt.setString(1, model);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cars.add(new Car(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("model"),
                            rs.getString("plate_number"),
                            rs.getInt("year"),
                            rs.getString("status")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
    // Filter cars by both status and model
    public List<Car> filterCarsByStatusAndModel(String status, String model) {
        List<Car> cars = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FILTER_CARS_BY_STATUS_AND_MODEL_SQL)) {

            stmt.setString(1, status);
            stmt.setString(2, model);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cars.add(new Car(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("model"),
                            rs.getString("plate_number"),
                            rs.getInt("year"),
                            rs.getString("status")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
}
