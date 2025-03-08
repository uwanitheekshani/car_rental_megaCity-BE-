package org.uwani.mega.megacity.dao;


import lombok.SneakyThrows;
import org.uwani.mega.megacity.config.DBConfig;
import org.uwani.mega.megacity.dto.ImageDToo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImageDAOo {
    @SneakyThrows
    public void saveImage(String imageName, String imageType, byte[] imageData) throws SQLException {
        String sql = "INSERT INTO imagy (image_name, image_type, image_data) VALUES (?, ?, ?)";

        try (Connection conn = DBConfig.getConnection();

             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, imageName);
            ps.setString(2, imageType);
            ps.setBytes(3, imageData);
            ps.executeUpdate();
        }
    }

    public byte[] getImage(Long id) throws SQLException {
        String sql = "SELECT image_data FROM imagy WHERE id = ?";
        try (Connection conn = DBConfig.getConnection();

             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBytes("image_data");
            }
        }
        return null; // If no image found.
    }

    public List<ImageDToo> getAllImages() {
        List<ImageDToo> images = new ArrayList<>();
        String sql = "SELECT id, image_name FROM imagy";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ImageDToo dto = new ImageDToo();
                dto.setId(rs.getLong("id"));
                dto.setImageName(rs.getString("image_name"));
                images.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }
}
