package org.uwani.mega.megacity.dto;

public class ProfileImageDTO {
    private int imageId;  // Primary Key of the image
    private String userEmail;  // User's email (can also use user_id depending on your design)
    private String imagePath;  // Path to the uploaded image
    private String uploadDate;  // Timestamp when the image was uploaded

    // Getters and Setters
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }
}
