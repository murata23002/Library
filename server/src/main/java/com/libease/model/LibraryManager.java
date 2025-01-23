package com.libease.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;


public class LibraryManager {
    private static LibraryManager instance = null;
    private final DataSource dataSource;

    private LibraryManager() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/testdb");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("rootpassword");
        dataSource = basicDataSource;
    }

    public static synchronized LibraryManager getInstance() {
        if (instance == null) {
            instance = new LibraryManager();
        }
        return instance;
    }
    
    /**
     * @param userId
     * @param userName
     * @return ユーザー情報
     * @throws SQLException
     */
    public User getUserInfoFromIdAndName(int userId, String userName) throws SQLException {
        User user = new User();
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT * FROM Users WHERE user_name = ? and user_id = ? ORDER BY Users.user_id ASC")) {
            stmt.setString(1, userName);
            stmt.setInt(2, userId);
            System.out.println("SQL: " + stmt.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("user_name"));
            }
        }
        return user;
    }

    public User getUserInfoFromName(String userName) throws SQLException {
        User user = new User();
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT * FROM Users WHERE user_name = ? ORDER BY Users.user_id ASC")) {
            stmt.setString(1, userName);
            System.out.println("SQL: " + stmt.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("user_name"));
            }
        }
        return user;
    }

    public List<Book> getBookingBooksByUserId(int user_id) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT booking.*, books.book_id, books.book_name, books.author");
        sb.append(" FROM booking");
        sb.append(" INNER JOIN books ON booking.book_id = books.book_id");
        sb.append(" WHERE booking.user_id = ?");
        sb.append(" ORDER BY booking.booking_id ASC");
        String sql = sb.toString();
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement(sql)) {
            stmt.setInt(1, user_id);
            System.out.println("SQL: " + stmt.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setBookName(rs.getString("book_name"));
                book.setAuthor(rs.getString("author"));
                bookList.add(book);
            }
        }
        return bookList;
    }

    public List<BookStatusData> getBooksBySearchQuery(String book_name, String author) throws SQLException {
        List<BookStatusData> bookList = new ArrayList<>();
        String sql = "SELECT books.book_id, books.book_name, books.author, " +
                "CASE " +
                "WHEN lending.book_id IS NOT NULL THEN 'borrowed' " +
                "WHEN booking.book_id IS NOT NULL THEN 'reserved' " +
                "ELSE 'available' " +
                "END AS status " +
                "FROM books " +
                "LEFT JOIN lending ON books.book_id = lending.book_id " +
                "LEFT JOIN booking ON books.book_id = booking.book_id " +
                "WHERE books.book_name LIKE ? " +
                "AND books.author LIKE ?" +
                " ORDER BY books.book_id ASC" +
                " LIMIT 100;";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement(sql)) {
            stmt.setString(1, "%" + book_name + "%");
            stmt.setString(2, "%" + author + "%");
            System.out.println("SQL: " + stmt.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BookStatusData book = new BookStatusData();
                book.setBook(
                        new Book(
                                rs.getInt("book_id"),
                                rs.getString("book_name"),
                                rs.getString("author")));
                book.setStatuts(rs.getString("status"));
                bookList.add(book);
            }
        }
        return bookList;
    }

    public Booking getBookingRecode(int book_id) throws SQLException {
        Booking booking = new Booking();
        String sql = "SELECT * " +
                "FROM booking " +
                "WHERE book_id = ? " +
                "ORDER BY booking_id ASC;";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement(sql)) {
            stmt.setInt(1, book_id);
            System.out.println("SQL: " + stmt.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                booking.setbookingId(rs.getInt("booking_id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setBookId(rs.getInt("book_id"));
            }
        }
        return booking;
    }

    public int createBookingRecode(int user_id, int book_id) throws SQLException {
        int errorCode = 0;
        String sql = "INSERT INTO booking (user_id, book_id) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user_id);
            stmt.setInt(2, book_id);
            System.out.println("SQL: " + stmt.toString());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                errorCode = 1;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            errorCode = 1;
        }
        return errorCode;
    }

    public int deleteBookingRecord(int user_id, int book_id) throws SQLException {
        int errorCode = 0;
        String sql = "DELETE FROM booking WHERE user_id = ? AND book_id = ?";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user_id);
            stmt.setInt(2, book_id);
            System.out.println("SQL: " + stmt.toString());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                errorCode = 1;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            errorCode = 1;
        }
        return errorCode;
    }

    public Lending getLendingRecode(int book_id) throws SQLException {
        Lending lending = new Lending();
        String sql = "SELECT * " +
                "FROM lending " +
                "WHERE book_id = ? " +
                " ORDER BY lending_id ASC";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement(sql)) {
            stmt.setInt(1, book_id);
            System.out.println("SQL: " + stmt.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                lending.setLendingId(rs.getInt("lending_id"));
                lending.setUserId(rs.getInt("user_id"));
                lending.setBookId(rs.getInt("book_id"));
            }
        }
        return lending;
    }

    public int createLendingRecode(int user_id, int book_id) throws SQLException {
        int errorCode = 0;
        String sql = "INSERT INTO lending (user_id, book_id) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user_id);
            stmt.setInt(2, book_id);
            System.out.println("SQL: " + stmt.toString());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                errorCode = 1;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            errorCode = 1;
        }
        return errorCode;
    }

    public int deleteLendingRecode(int user_id, int book_id) throws SQLException {
        int errorCode = 0;
        String sql = "DELETE FROM lending WHERE user_id = ? AND book_id = ?";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user_id);
            stmt.setInt(2, book_id);
            System.out.println("SQL: " + stmt.toString());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                errorCode = 1;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            errorCode = 1;
        }
        return errorCode;
    }

    public List<User> getUsersInfoByQuery(int userId, String userName) throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT * FROM Users WHERE user_id = ? or user_name Like ?")) {
            stmt.setInt(1, userId);
            stmt.setString(2, "%" + userName + "%");
            String sql = stmt.toString();
            System.out.println("SQL: " + sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("user_name"));
                users.add(user);
            }
        }
        return users;
    }

    public void sample() throws SQLException {
        boolean isThrowException = true;
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement1 = connection.prepareStatement("");
                    PreparedStatement statement2 = connection.prepareStatement("#")) {
                statement1.executeUpdate();
                if (isThrowException) {
                    throw new SQLException();
                }
                statement2.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }
}
