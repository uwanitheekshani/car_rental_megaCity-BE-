package org.uwani.mega.megacity.service;

import java.io.InputStream;

public interface ImageServicee {
    void saveImage(String imageName, String imageType, InputStream imageInputStream) throws Exception;
    byte[] getImage(Long imageId) throws Exception;
    public String getAllImagesJSON();

}
