package org.uwani.mega.megacity.dto;

public class CarDTO {
    private int id;
    private String name;
    private String model;
    private String plate_number;
    private int year;
    private String status;

    @Override
    public String toString() {
        return "CarDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", plate_number='" + plate_number + '\'' +
                ", year=" + year +
                ", status='" + status + '\'' +
                '}';
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CarDTO(int id, String name, String model, String plate_number, int year, String status) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.plate_number = plate_number;
        this.year = year;
        this.status = status;
    }

    public CarDTO(int carId) {
    }
}