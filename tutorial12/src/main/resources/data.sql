INSERT INTO roles (id, name)
VALUES (1, 'ROLE_READER'),
       (2, 'ROLE_PUBLISHER'),
       (3, 'ROLE_LIBRARIAN'),
       (4, 'ROLE_ADMIN');

INSERT INTO users (id, username, email, password)
VALUES (1, 'alice', 'alice@example.com', '{bcrypt}$2a$12$SZxXLgVfttxipqXkogixNuf4jbZY7QGRm4Uizqu6k/PcsJEv5.oim'),
       (2, 'bob', 'bob@example.com', '{bcrypt}$2a$12$SZxXLgVfttxipqXkogixNuf4jbZY7QGRm4Uizqu6k/PcsJEv5.oim'),
       (3, 'carol', 'carol@example.com', '{bcrypt}$2a$12$SZxXLgVfttxipqXkogixNuf4jbZY7QGRm4Uizqu6k/PcsJEv5.oim'),
       (4, 'dave', 'dave@example.com', '{bcrypt}$2a$12$SZxXLgVfttxipqXkogixNuf4jbZY7QGRm4Uizqu6k/PcsJEv5.oim'),
       (5, 'eve', 'eve@example.com', '{bcrypt}$2a$12$SZxXLgVfttxipqXkogixNuf4jbZY7QGRm4Uizqu6k/PcsJEv5.oim');

INSERT INTO USERS_ROLES (user_id, role_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 1);

INSERT INTO AUTHOR (authorID, firstName, lastName)
VALUES (1, 'Jane', 'Austen'),
       (2, 'George', 'Orwell'),
       (3, 'J.K.', 'Rowling'),
       (4, 'Isaac', 'Asimov'),
       (5, 'Yuval', 'Harari');

INSERT INTO GENRE (GENREID, genreName)
VALUES (1, 'Fiction'),
       (2, 'Science Fiction'),
       (3, 'Fantasy'),
       (4, 'Non-Fiction'),
       (5, 'Historical');


INSERT INTO publisher (publisherID, publisherName, address, country)
VALUES (1, 'Penguin Random House', '1745 Broadway, New York, NY', 'USA'),
       (2, 'HarperCollins', '195 Broadway, New York, NY', 'USA');


INSERT INTO book (bookID, title, authorID, publisherID, publicationYear, isbn)
VALUES (1, 'Pride and Prejudice', 1, 1, 1813, '9780141439518'),
       (2, '1984', 2, 1, 1949, '9780451524935'),
       (3, 'Harry Potter and the Sorcerer''s Stone', 3, 2, 1997, '9780590353427'),
       (4, 'Foundation', 4, 2, 1951, '9780553293357'),
       (5, 'Sapiens: A Brief History of Humankind', 5, 1, 2011, '9780062316097');

INSERT INTO BOOKGENRE (bookid, genreid)
VALUES (1, 1),
       (1, 5),
       (2, 1),
       (2, 2),
       (3, 3),
       (3, 1),
       (4, 2),
       (5, 4);

INSERT INTO reviews (ID, BOOK_ID, user_id, rating, content)
VALUES (1, 1, 1, 5, 'An all-time classic—timeless and delightful'),
       (2, 2, 5, 4, 'A chilling vision of a dystopian future'),
       (3, 3, 1, 5, 'A magical journey that captivated me from the start'),
       (4, 5, 5, 5, 'Fascinating read; truly makes you rethink human history');

DELETE FROM BOOKGENRE;
DELETE FROM BOOK;
DELETE FROM AUTHOR;
DELETE FROM GENRE;
DELETE FROM PUBLISHER;
DELETE FROM REVIEWS;
DELETE FROM ROLES;
DELETE FROM USERS;
DELETE FROM USERS_ROLES;