package org.uwani.mega.megacity.servlet;

import com.google.gson.Gson;
//import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.uwani.mega.megacity.dto.CarDTO;
import org.uwani.mega.megacity.dto.ImageDTO;
import org.uwani.mega.megacity.service.CarService;
import org.uwani.mega.megacity.service.ImageService;
import org.uwani.mega.megacity.service.impl.CarServiceImpl;
import org.uwani.mega.megacity.service.impl.ImageServiceImpl;

//import java.io.BufferedReader;
//import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/uploadCarWithImage/*")
//@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
//        maxFileSize = 1024 * 1024 * 10,    // 10MB
//        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class CarImageServlet extends HttpServlet {
    private CarService carService = new CarServiceImpl();
    private ImageService imageService = new ImageServiceImpl();

    // Handle GET request to retrieve cars or images
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Get all cars
            getAllCars(response);
        } else if (pathInfo.contains("images")) {
            // Get images for a specific car
            getCarImages(request, response);
        } else {
            sendErrorResponse(response, "Invalid path for GET request.");
        }
    }

    // Handle POST request to add a new car with image
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addCarWithImage(request, response);
    }

    // Handle PUT request to update car with an optional image update
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateCarWithImage(request, response);
    }

    // Handle DELETE request to delete a car and its images
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        deleteCar(request, response);
    }


    // Add a car with an image
    private void addCarWithImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String model = request.getParameter("model");
        String plate_number = request.getParameter("plate_number");
        int year = Integer.parseInt(request.getParameter("year"));
        String status = request.getParameter("status");


        // Create CarDTO object
        CarDTO carDTO = new CarDTO(0, name, model, plate_number, year, status);

        // Call service to add car and populate the carDTO with the ID
        carService.addCar(carDTO);
        int carId = carDTO.getId();  // Get the ID of the newly added car

        // Handle image upload if present
        // Part filePart = request.getPart("image");
//        if (filePart != null && filePart.getSize() > 0) {
//            String fileName = Path.of(filePart.getSubmittedFileName()).getFileName().toString();
//            String filePath = getServletContext().getRealPath("/") + "uploads" + File.separator + fileName;
//
//            // Ensure uploads directory exists
//            File uploadsDir = new File(getServletContext().getRealPath("/") + "uploads");
//            if (!uploadsDir.exists()) {
//                uploadsDir.mkdirs();
//            }
//
//            // Write the file to disk
//            filePart.write(filePath);
//
//            // Create ImageDTO object
//            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
//            ImageDTO imageDTO = new ImageDTO(0, fileName, filePath, carId, createdAt);
//
//            // Call service to add the image
//            imageService.addImage(imageDTO);
//        }

        sendSuccessResponse(response, "Car and Image added successfully.");
    }

    // Get all cars
    private void getAllCars(HttpServletResponse response) throws IOException {
        List<CarDTO> cars = carService.getAllCars();
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(cars));
    }

    // Get images for a specific car
    private void getCarImages(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int carId = Integer.parseInt(request.getParameter("carId"));
        List<ImageDTO> images = imageService.getImagesByCarId(carId);
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(images));
    }

    // Update car with an optional image update
    private void updateCarWithImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String carIdParam = request.getParameter("carId");
        String yearParam = request.getParameter("year");

        // Log incoming request parameters for debugging
        System.out.println("Received carId: " + carIdParam);
        System.out.println("Received year: " + yearParam);

        if (carIdParam == null || yearParam == null || carIdParam.isEmpty() || yearParam.isEmpty()) {
            sendErrorResponse(response, "Car ID or Year is missing.");
            return;
        }

        int carId = Integer.parseInt(carIdParam);
        int year = Integer.parseInt(yearParam);

        CarDTO carDTO = carService.getCarById(carId);

        if (carDTO != null) {
            // Update car details
            carDTO.setName(request.getParameter("name"));
            carDTO.setModel(request.getParameter("model"));
            carDTO.setPlate_number(request.getParameter("plate_number"));
            carDTO.setYear(year);  // Set the parsed year
            carDTO.setStatus(request.getParameter("status"));

            System.out.println("////name :"+carDTO.getName());

            // Call service to update car
            carService.updateCar(carDTO);



            sendSuccessResponse(response, "Car updated successfully.");
        } else {
            sendErrorResponse(response, "Car not found.");
        }
    }

    // Delete car and its associated images
    private void deleteCar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int carId = Integer.parseInt(request.getParameter("carId"));
        CarDTO carDTO = carService.getCarById(carId);

        if (carDTO != null) {
            imageService.deleteImagesByCarId(carId);
            carService.deleteCar(carId);
            sendSuccessResponse(response, "Car and associated images deleted successfully.");
        } else {
            sendErrorResponse(response, "Car not found.");
        }
    }

    // Send success response with message
    private void sendSuccessResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(new ResponseMessage(message)));
    }

    // Send error response with message
    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(new ResponseMessage(message)));
    }

    // Response message class for consistent JSON response
    private static class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}