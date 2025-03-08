package org.uwani.mega.megacity.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/uploadProfileImage")
@MultipartConfig
public class ProfileImageUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Get the uploaded file
        Part filePart = request.getPart("image");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        if (filePart == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"No file uploaded\"}");
            return;
        }

        // Get the real path to the server's directory and ensure correct path formatting
        String uploadDir = getServletContext().getRealPath("/") + "uploads" + File.separator;

        // Ensure the uploads directory exists
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();  // Create the directory if it doesn't exist
        }

        // Save the file to the specified directory
        File file = new File(uploadDir + fileName);
        filePart.write(file.getAbsolutePath());  // Save the file

        // Optional: Store the file path in the database if needed
        // Example: Update user's profile image URL in the database using userEmail or userId

        response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
        response.getWriter().write("{\"message\":\"Image uploaded successfully\"}");
    }
}
