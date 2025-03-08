package org.uwani.mega.megacity.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.uwani.mega.megacity.dto.CarDTO;
import org.uwani.mega.megacity.service.CarUserFilteringService;
import org.uwani.mega.megacity.service.impl.CarUserFilteringServiceimpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/filteringUserCars")
public class CarUserFiltering extends HttpServlet {
    private CarUserFilteringService carService = new CarUserFilteringServiceimpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = request.getParameter("status");
        String model = request.getParameter("model");

        List<CarDTO> cars;

        // Apply the filters based on parameters
        if (status != null && model != null) {
            cars = carService.filterCarsByStatusAndModel(status, model);
        } else if (status != null) {
            cars = carService.filterCarsByStatus(status);
        } else if (model != null) {
            cars = carService.filterCarsByModel(model);
        } else {
            cars = carService.getAllCars();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Convert the list of CarDTO to JSON and send it as a response
            out.write(new com.google.gson.Gson().toJson(cars));
            out.flush();
        }
    }
}
