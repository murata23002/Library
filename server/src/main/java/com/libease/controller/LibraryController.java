package com.libease.controller;

import java.sql.SQLException;
import java.util.List;

import com.libease.model.Book;
import com.libease.model.BookStatusData;
import com.libease.model.LibraryManager;

public class LibraryController {
    public List<BookStatusData> searchExecution(String bookName, String author) throws SQLException {
        bookName = (bookName == null) ? "" : bookName;
        author = (author == null) ? "" : author;

        LibraryManager libManager = LibraryManager.getInstance();
        List<BookStatusData> bookStatusDatas = libManager.getBooksBySearchQuery(bookName, author);
        return bookStatusDatas;
    }

    public  List<Book> findReservedBooks(int userId) throws SQLException {
        LibraryManager libManager = LibraryManager.getInstance();
        List<Book> books = libManager.getBookingBooksByUserId(userId);
        return books;
    }

    public int bookingExecution(int userId, int bookId) throws SQLException {
        LibraryManager libManager = LibraryManager.getInstance();
        int errorCode = libManager.createBookingRecode(userId,bookId);
        return errorCode;
    }

    public int cancelBookingExecution(int userId, int bookId) throws SQLException {
        LibraryManager libManager = LibraryManager.getInstance();
        int errorCode = libManager.deleteBookingRecord(userId,bookId);
        return errorCode;
    }
}
