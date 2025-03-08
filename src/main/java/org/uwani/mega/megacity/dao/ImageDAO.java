package org.uwani.mega.megacity.dao;


import org.uwani.mega.megacity.config.DBConfig;
import org.uwani.mega.megacity.entity.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImageDAO {

    private static final String ADD_IMAGE_SQL = "INSERT INTO images (name, path, car_id) VALUES (?, ?, ?)";
    private static final String GET_IMAGES_BY_CAR_ID_SQL = "SELECT * FROM images WHERE car_id = ?";
    private static final String DELETE_IMAGES_BY_CAR_ID_SQL = "DELETE FROM images WHERE car_id = ?";

    // Method to add a new image
    public void addImage(Image image) {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(ADD_IMAGE_SQL)) {

            stmt.setString(1, image.getName());
            stmt.setString(2, image.getPath());
            stmt.setInt(3, image.getCarId()); // Foreign key reference to the car

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get all images associated with a car ID
    public List<Image> getImagesByCarId(int carId) {
        List<Image> images = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_IMAGES_BY_CAR_ID_SQL)) {
            stmt.setInt(1, carId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                images.add(new Image(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("path"),
                        rs.getInt("car_id"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }



    // Method to delete images by car ID
    public void deleteImagesByCarId(int carId) {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_IMAGES_BY_CAR_ID_SQL)) {

            stmt.setInt(1, carId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
