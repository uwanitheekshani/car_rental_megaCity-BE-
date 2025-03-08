package org.uwani.mega.megacity.service.impl;


import com.google.gson.Gson;
import org.uwani.mega.megacity.dao.ImageDAOo;
import org.uwani.mega.megacity.dto.ImageDToo;
import org.uwani.mega.megacity.service.ImageServicee;

import java.io.InputStream;
import java.util.List;

public class ImageServiceImpll implements ImageServicee {
    private ImageDAOo imageDAO = new ImageDAOo();

    @Override
    public void saveImage(String imageName, String imageType, InputStream imageInputStream) throws Exception {
        // Read input stream into byte array
        byte[] imageData = imageInputStream.readAllBytes();
        imageDAO.saveImage(imageName, imageType, imageData);
    }

    @Override
    public byte[] getImage(Long imageId) throws Exception {
        return imageDAO.getImage(imageId);
    }
    @Override
    public String getAllImagesJSON() {
        List<ImageDToo> images = imageDAO.getAllImages(); // Fetch images from DAO
        return new Gson().toJson(images); // Convert to JSON
    }
}
