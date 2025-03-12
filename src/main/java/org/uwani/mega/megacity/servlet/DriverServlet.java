package org.uwani.mega.megacity.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.uwani.mega.megacity.entity.Booking;
import org.uwani.mega.megacity.service.BookingService;
import org.uwani.mega.megacity.service.impl.BookingServiceImpl;

import java.io.IOException;
import java.util.List;


@WebServlet("/driver")
public class DriverServlet extends HttpServlet {

    private final BookingService bookingService = new BookingServiceImpl();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (idParam != null) { // Get booking by ID
            int id = Integer.parseInt(idParam);// Get all bookings
            List<Booking> bookings = bookingService.getAllBookings(id);
            resp.getWriter().write(gson.toJson(bookings));
        }
    }
}