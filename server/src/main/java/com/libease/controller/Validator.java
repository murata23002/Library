package com.libease.controller;

import java.sql.SQLException;

import com.libease.model.LibraryManager;
import com.libease.model.User;

public class Validator {
    public static String loginValidation (int userId, String userName) throws SQLException {
        String errorMsg = MessageManager.ERROR_LOGIN;
        LibraryManager lManager = LibraryManager.getInstance();
        User user = lManager.getUserInfoFromIdAndName(userId, userName);
        
        if (user.getId() == 0)  return errorMsg; 
        return "";
    }

    public static String bookingValidation(int userId, int bookId) throws SQLException {
        String errorMsg = MessageManager.ERROR_BOOKING;
        if (userId <= 0)
            return errorMsg;
        if (bookId <= 0)
            return errorMsg;
        return "";
    }

    public static String cancelValidation(int userId, int bookId) throws SQLException {
        String errorMsg = MessageManager.ERROR_CANCEL;
        if (userId <= 0)
            return errorMsg;
        if (bookId <= 0)
            return errorMsg;
        return "";
    }
}
