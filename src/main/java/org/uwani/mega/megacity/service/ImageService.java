package org.uwani.mega.megacity.service;


import org.uwani.mega.megacity.dto.ImageDTO;

import java.util.List;

public interface ImageService {
    void addImage(ImageDTO imageDTO);
    List<ImageDTO> getImagesByCarId(int carId);
    void deleteImagesByCarId(int carId);
}
