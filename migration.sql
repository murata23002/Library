-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

-- Create tables
CREATE TABLE Book (
  bookId INT PRIMARY KEY AUTO_INCREMENT,
  bookName VARCHAR(255) NOT NULL,
  author VARCHAR(255) NOT NULL
);

CREATE TABLE User (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE Lending (
  lendingId INT PRIMARY KEY AUTO_INCREMENT,
  userId INT,
  bookId INT,
  FOREIGN KEY (userId) REFERENCES User(id),
  FOREIGN KEY (bookId) REFERENCES Book(bookId)
);

CREATE TABLE Booking (
  bookingId INT PRIMARY KEY AUTO_INCREMENT,
  userId INT,
  bookId INT,
  FOREIGN KEY (userId) REFERENCES User(id),
  FOREIGN KEY (bookId) REFERENCES Book(bookId)
);

CREATE TABLE BookStatusData (
  bookId INT,
  status VARCHAR(255),
  FOREIGN KEY (bookId) REFERENCES Book(bookId)
);

INSERT INTO Book (bookName, author) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald'),
('To Kill a Mockingbird', 'Harper Lee'),
('1984', 'George Orwell');

INSERT INTO User (name) VALUES
('John Doe'),
('Jane Smith'),
('Emily Johnson');

INSERT INTO Lending (userId, bookId) VALUES
(1, 1), -- John Doe borrows "The Great Gatsby"
(2, 2), -- Jane Smith borrows "To Kill a Mockingbird"
(3, 3); -- Emily Johnson borrows "1984"

INSERT INTO Booking (userId, bookId) VALUES
(2, 1), -- Jane Smith books "The Great Gatsby"
(3, 2); -- Emily Johnson books "To Kill a Mockingbird"

INSERT INTO BookStatusData (bookId, status) VALUES
(1, 'available'),
(2, 'borrowed'),
(3, 'available');
