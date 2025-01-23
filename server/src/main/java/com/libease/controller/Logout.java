package com.libease.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;

import com.libease.common.PathManager;

@WebServlet(urlPatterns = { "/logout" })
public class Logout extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            UserController userCon = new UserController();
            userCon.Logout(request);
            request.getSession().setAttribute("successMessage", MessageManager.SUCCESS_LOGOUT);
            response.sendRedirect(request.getContextPath());
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher(PathManager.ERROR).forward(request, response);
        }
    }
}
