package com.libease.controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libease.common.LibraryCommon;
import com.libease.common.PathManager;
import com.libease.model.Book;

@WebServlet(urlPatterns = { "/bookingExecutions" })
public class BookingExecutions extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Book book = getMappedBookValueFromSession(request);
            int userId = LibraryCommon.getUserIdFromSession(request);
            String message = Validator.bookingValidation(userId, book.getBookId());
            if (!message.isEmpty()) {
                request.getSession().setAttribute("errorMessage", message);
                return;
            }
            LibraryController libCon = new LibraryController();
            int erroCode = libCon.bookingExecution(userId, book.getBookId());
            if (erroCode == 1) {
                request.getSession().setAttribute("errorMessage", MessageManager.ERROR_BOOKING);
                return;
            }
            // エラーが無ければ成功メッセージを格納する
            request.getSession().setAttribute("successMessage", MessageManager.SUCCESS_BOOKING);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
			request.getRequestDispatcher(PathManager.ERROR).forward(request, response);
        }
    }

    private Book getMappedBookValueFromSession(HttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = LibraryCommon.getJsoStringFromRequest(request);
        Book book = mapper.readValue(json, Book.class);
        return book;
    }
}
