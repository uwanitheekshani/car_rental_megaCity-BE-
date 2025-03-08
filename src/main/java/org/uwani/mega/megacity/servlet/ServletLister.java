package org.uwani.mega.megacity.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/list-servlets")  // URL to access this servlet
public class ServletLister extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        ServletContext context = getServletContext();

        response.getWriter().println("Registered Servlets 🌐:");

        Map<String, ? extends javax.servlet.ServletRegistration> servlets = context.getServletRegistrations();
        for (Map.Entry<String, ? extends javax.servlet.ServletRegistration> entry : servlets.entrySet()) {
            response.getWriter().println(" - " + entry.getKey() + " -> " + entry.getValue().getMappings());
        }
    }
}
