package com.libease.model;

public class Booking {
    private int bookingId;
    private int userId;
    private int bookId;
    
    public int getbookingId() {
        return bookingId;
    }
    
    public void setbookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getBookId() {
        return bookId;
    }
    
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Booking [bookingId=" + bookingId + ", userId=" + userId + ", bookId=" + bookId + "]";
    }
}
