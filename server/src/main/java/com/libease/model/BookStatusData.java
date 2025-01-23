package com.libease.model;

public class BookStatusData {
    private Book book = null;
    private String statuts;

    public BookStatusData() {
    }

    public BookStatusData(Book book, String statuts) {
        this.book = book;
        this.statuts = statuts;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getStatuts() {
        return statuts;
    }

    public void setStatuts(String statuts) {
        this.statuts = statuts;
    }

    @Override
    public String toString() {
        return "BookStatusData [book=" + book + ", statuts=" + statuts + "]";
    }
}
