package org.uwani.mega.megacity.dao;


import org.uwani.mega.megacity.config.DBConfig;
import org.uwani.mega.megacity.entity.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {

    private static final String ADD_CAR_SQL = "INSERT INTO cars (name, model, plate_number, year, status) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_CAR_BY_ID_SQL = "SELECT * FROM cars WHERE id = ?";
    private static final String GET_ALL_CARS_SQL = "SELECT * FROM cars";
    private static final String UPDATE_CAR_SQL = "UPDATE cars SET name = ?, model = ?, plate_number = ?, year = ?, status = ? WHERE id = ?";
    private static final String DELETE_CAR_SQL = "DELETE FROM cars WHERE id = ?";

    // Method to add a new car
    public void addCar(Car car) {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(ADD_CAR_SQL)) {

            stmt.setString(1, car.getName());
            stmt.setString(2, car.getModel());
            stmt.setString(3, car.getPlate_number());
            stmt.setInt(4, car.getYear());
            stmt.setString(5, car.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get a car by its ID
    public Car getCarById(int id) {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_CAR_BY_ID_SQL)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Car(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("model"),
                        rs.getString("plate_number"),
                        rs.getInt("year"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to get all cars
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(GET_ALL_CARS_SQL);
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
            e.printStackTrace();
        }
        return cars;
    }

    // Method to update a car
    public void updateCar(Car car) {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_CAR_SQL)) {

            stmt.setString(1, car.getName());
            stmt.setString(2, car.getModel());
            stmt.setString(3, car.getPlate_number());
            stmt.setInt(4, car.getYear());
            stmt.setString(5, car.getStatus());
            stmt.setInt(6, car.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a car
    public void deleteCar(int id) {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_CAR_SQL)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCarStatus(int carId, String status) throws SQLException {
        String sql = "UPDATE cars SET status = ? WHERE id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, carId);
            ps.executeUpdate();
        }
    }

}
