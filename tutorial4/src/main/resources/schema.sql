DROP TABLE IF EXISTS Author;
DROP TABLE IF EXISTS Book;
DROP TABLE IF EXISTS Publisher;
DROP TABLE IF EXISTS Author_Book;

CREATE TABLE Author(
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

CREATE TABLE Publisher(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE Book(
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    publisher_id INT NOT NULL,
    CONSTRAINT fk_publisher FOREIGN KEY (publisher_id) REFERENCES Publisher(id)
);

CREATE TABLE Author_Book(
    book_id INT,
    author_id INT,
    PRIMARY KEY (book_id, author_id)
);