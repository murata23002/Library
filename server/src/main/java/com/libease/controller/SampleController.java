package com.libease.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;

import com.libease.common.PathManager;
import com.libease.model.LibraryManager;
import com.libease.model.User;

@WebServlet(urlPatterns = {"/index"})
public class SampleController extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			LibraryManager libManager = LibraryManager.getInstance();
			List<User> users;
			users = libManager.getUsersInfoByQuery(1,"");
			request.setAttribute("userList", users);
			RequestDispatcher dispatcher = request.getRequestDispatcher("");
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			request.getRequestDispatcher(PathManager.ERROR).forward(request, response);
		}
	}
}
