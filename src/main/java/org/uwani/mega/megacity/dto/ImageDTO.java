package org.uwani.mega.megacity.dto;

import java.sql.Timestamp;

public class ImageDTO {
    private int id;
    private String name;
    private String path;
    private int carId;
    private Timestamp createdAt;

    // Constructors
    public ImageDTO() {}

    public ImageDTO(int id, String name, String path, int carId, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.carId = carId;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
