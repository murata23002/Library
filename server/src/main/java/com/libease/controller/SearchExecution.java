package com.libease.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;

import com.libease.common.LibraryCommon;
import com.libease.common.PathManager;
import com.libease.model.Book;
import com.libease.model.BookStatusData;

@WebServlet(urlPatterns = { "/searchExecution" })
public class SearchExecution extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			LibraryController libCon = new LibraryController();

			int userId = LibraryCommon.getUserIdFromSession(request);
			List<Book> books = libCon.findReservedBooks(userId);
			request.setAttribute("books", books);

			String bookName = request.getParameter("bookName");
			String author = request.getParameter("author");
			List<BookStatusData> bookStatusDatas = libCon.searchExecution(bookName, author);
			request.setAttribute("bookStatusDatas", bookStatusDatas);

			request.getRequestDispatcher(PathManager.BOOKING_BOOK).forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			request.getRequestDispatcher(PathManager.ERROR).forward(request, response);
		}
	}
}
