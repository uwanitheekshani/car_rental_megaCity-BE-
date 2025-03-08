package org.uwani.mega.megacity.filters;

import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/secure/*") // Protects endpoints under /secure/*
public class JwtFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String authHeader = req.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("{ \"error\": \"Missing or invalid token\" }");
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = JwtUtil.verifyToken(token);
            request.setAttribute("role", claims.get("role")); // Store role in request
            chain.doFilter(request, response);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("{ \"error\": \"Invalid token\" }");
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {}

    public void destroy() {}
}
