package org.uwani.mega.megacity.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.uwani.mega.megacity.dto.ProfileImageDTO;
import org.uwani.mega.megacity.service.UserService;
import org.uwani.mega.megacity.service.impl.UserServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/viewProfileImage")
public class ProfileImageViewServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");

        String email = request.getParameter("email");  // Get email parameter
        if (email == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Email is required\"}");
            return;
        }

        // Get the image path from the database
        ProfileImageDTO profileImageDTO = userService.getProfileImage(email);
        if (profileImageDTO == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\":\"No profile image found\"}");
            return;
        }

        // Serve the image file from the 'uploads/' directory
        File imageFile = new File(getServletContext().getRealPath("") + File.separator + profileImageDTO.getImagePath());
        if (!imageFile.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\":\"Image file not found\"}");
            return;
        }

        // Stream the image content to the client
        FileInputStream fis = new FileInputStream(imageFile);
        OutputStream os = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        fis.close();
        os.flush();
        os.close();
    }
}
