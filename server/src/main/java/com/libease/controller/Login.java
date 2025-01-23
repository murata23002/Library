package com.libease.controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;

@WebServlet(urlPatterns = { "/login" })
public class Login extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = convertToInt(request.getParameter("userId"));
        String userName = request.getParameter("userName");
        try {
            String errorMsg = Validator.loginValidation(userId, userName);
            if (errorMsg != "") {
                request.getSession().setAttribute("errorMessage", errorMsg);
            } else {
                UserController UserController = new UserController();
                UserController.Login(request, userId, userName);
                request.getSession().setAttribute("successMessage", MessageManager.SUCCESS_LOGIN);
            }
            RequestDispatcher rd = request.getRequestDispatcher("");
            rd.forward(request, response);
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    private int convertToInt(String str) {
        int result = 0;
        if (str != null && str.matches("\\d+")) {
            try {
                result = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                // パースに失敗した場合は0を返す
            }
        }
        return result;
    }
}
