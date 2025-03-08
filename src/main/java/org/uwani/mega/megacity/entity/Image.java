package org.uwani.mega.megacity.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;


@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Image(String name, String path, int carId, Timestamp createdAt) {

        this.name = name;
        this.path = path;
        this.carId = carId;
        this.createdAt = createdAt;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Image(int id, String name, String path, int carId, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.carId = carId;
        this.createdAt = createdAt;
    }

    private String name;
    private String path;
    private int carId;

    public Image() {
    }

    private Timestamp createdAt;

}