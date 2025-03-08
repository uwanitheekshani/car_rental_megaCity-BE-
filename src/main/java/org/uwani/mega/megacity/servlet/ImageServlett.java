package org.uwani.mega.megacity.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.uwani.mega.megacity.service.ImageServicee;
import org.uwani.mega.megacity.service.impl.ImageServiceImpll;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/image")
public class ImageServlett extends HttpServlet {
    private ImageServicee imageService = new ImageServiceImpll();

    // Handling Image Upload
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageName = request.getParameter("imageName");
        String imageType = request.getContentType();
        InputStream imageInputStream = request.getInputStream();

        try {
            imageService.saveImage(imageName, imageType, imageInputStream);
            response.getWriter().write("Image uploaded successfully!");
        } catch (Exception e) {
            response.getWriter().write("Error uploading image: " + e.getMessage());
        }
    }

    // Handling Image Retrieval
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageId = request.getParameter("imageId");

        try {
            if (imageId != null && !imageId.isEmpty()) {
                Long id = Long.parseLong(imageId);
                byte[] imageData = imageService.getImage(id);
                if (imageData != null) {
                    response.setContentType("image/jpeg");
                    response.getOutputStream().write(imageData);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("Image not found.");
                }
            } else {
                // ✅ Return list of images in JSON format
                response.setContentType("application/json");
                response.getWriter().write(imageService.getAllImagesJSON());
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid image ID.");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error retrieving images: " + e.getMessage());
        }
    }

}
