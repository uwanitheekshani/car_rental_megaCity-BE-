package org.uwani.mega.megacity.servlet;


import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.uwani.mega.megacity.entity.User;
import org.uwani.mega.megacity.service.UserManageService;
import org.uwani.mega.megacity.service.impl.UserManageServiceimpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/users/*")
public class UserManageServlet extends HttpServlet {

    private UserManageService userService = new UserManageServiceimpl();
// we can't make more than 1 get methods
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve query parameters
        String email = request.getParameter("email");
        String role = request.getParameter("role");

        // Determine which service method to call
        List<User> users;
        if (email != null && !email.isEmpty()) {
            users = userService.getUsersByEmail(email);  // Get users by email
        } else if (role != null && !role.isEmpty()) {
            users = userService.getUsersByRole(role);  // Get users by role
        } else {
            users = userService.getAllUsers();  // Get all users
        }

        // Set response type and send the result back as JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(users));  // Convert list of users to JSON
        out.flush();
    }

    // Other methods (doPut, doDelete) for updating and deleting users remain the same

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the userId from the URL path
        String userId = request.getPathInfo() != null ? request.getPathInfo().substring(1) : null;

        if (userId == null || userId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("User ID is missing.");
            return;
        }

        try {
            // Parse the userId to an integer
            int id = Integer.parseInt(userId);

            // Read the request body for updated user data
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }

            String jsonData = sb.toString();
            Gson gson = new Gson();
            User updatedUser = gson.fromJson(jsonData, User.class);

            // Use the service to update the user
            boolean updated = userService.updateUser(id, updatedUser);

            response.setContentType("application/json");
            response.getWriter().write("{\"updated\": " + updated + "}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid user ID format.");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error updating user: " + e.getMessage());
        }
    }


    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the id from the URL path
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            response.getWriter().write("Missing user ID.");
            return;
        }

        String userId = pathInfo.substring(1); // Remove the leading slash
        try {
            int id = Integer.parseInt(userId);  // This will throw NumberFormatException if invalid

            boolean isDeleted = userService.deleteUser(id); // Assuming deleteUserById is your service method
            if (isDeleted) {
                response.setStatus(HttpServletResponse.SC_OK); // 200 OK
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
                response.getWriter().write("User not found.");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request for invalid ID
            response.getWriter().write("Invalid ID format.");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            response.getWriter().write("An error occurred while processing the request.");
            e.printStackTrace(); // Print error for debugging
        }
    }

}
