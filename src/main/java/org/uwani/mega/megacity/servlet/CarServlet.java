package org.uwani.mega.megacity.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import org.uwani.mega.megacity.service.CarService;
import org.uwani.mega.megacity.service.impl.CarServiceImpl;


@WebServlet("/car/*")

public class CarServlet extends HttpServlet {

    private CarService carService = new CarServiceImpl();

//
//    @Override
//    // Handle POST request to add a car
//    @SneakyThrows
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
//        String json = request.getReader().readLine();
//        CarDTO carDTO = new Gson().fromJson(json, CarDTO.class);
//
//        boolean success = carService.addCar(carDTO);
//
//        if (success) {
//            response.setStatus(HttpServletResponse.SC_CREATED); // 201 Created
//            response.getWriter().write("{\"message\": \"Car added successfully!\"}");
//        } else {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
//            response.getWriter().write("{\"error\": \"Failed to add car.\"}");
//        }
//    }


//
//    @SneakyThrows
//    @Override
//    // Handle PUT request to update a car
//    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        String id = request.getPathInfo() != null ? request.getPathInfo().substring(1) : ""; // Remove leading slash
//        System.out.println(id);
//        // Parse the incoming JSON body
//        Car car = new Gson().fromJson(request.getReader(), Car.class);
//
//        // Call the service layer to update the car
//        boolean isUpdated = carService.updateCar(id, car);
//        System.out.println(isUpdated);
//        // Prepare the response
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        PrintWriter out = response.getWriter();
//        if (isUpdated) {
//            response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
//            out.write("{\"message\": \"Car updated successfully!\"}");
//        } else {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 Bad Request
//            out.write("{\"error\": \"Failed to update car\"}");
//        }
//
//        out.flush();
//    }


    // Handle DELETE request to delete a car
//    @Override
//    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String idString = request.getPathInfo() != null ? request.getPathInfo().substring(1) : null; // Get ID from URL
//
//        // Check if the id is valid
//        if (idString == null || idString.isEmpty()) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 Bad Request
//            response.getWriter().write("{\"error\": \"Car ID is required.\"}");
//            return;
//        }
//
//        // Try parsing the id to an integer
//        try {
//            int id = Integer.parseInt(idString);  // Parse ID to integer
//
//            // Call service to delete the car
//            boolean isDeleted = carService.deleteCar(id);
//
//            // Prepare the response
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//
//            PrintWriter out = response.getWriter();
//            if (isDeleted) {
//                response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
//                out.write("{\"message\": \"Car deleted successfully!\"}");
//            } else {
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 Bad Request
//                out.write("{\"error\": \"Failed to delete car.\"}");
//            }
//
//            out.flush();
//
//        } catch (NumberFormatException e) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 Bad Request
//            response.getWriter().write("{\"error\": \"Invalid car ID format.\"}");
//        }
//    }
//
//    // Handle GET request to retrieve all cars
//    @SneakyThrows
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
//        List<CarDTO> cars = carService.getAllCars();
//
//        if (cars != null && !cars.isEmpty()) {
//            response.setStatus(HttpServletResponse.SC_OK); // 200 OK
//            response.getWriter().write(new Gson().toJson(cars));
//        } else {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
//            response.getWriter().write("{\"error\": \"No cars found.\"}");
//        }
//    }
}
