DROP DATABASE IF EXISTS Library;
CREATE DATABASE Library;

USE Library;

CREATE USER 'library'@'localhost';
GRANT ALL ON Library.* TO 'library'@'localhost' IDENTIFIED BY 'V3ry5ecretC0de';

DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS loans;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS book_copies;
DROP TABLE IF EXISTS book_details;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS genres;

CREATE TABLE roles(
    role_id int AUTO_INCREMENT,
    name varchar(10),
    description varchar(255),
    CONSTRAINT pk_roles PRIMARY KEY(role_id)
);

CREATE TABLE users(
    user_id int AUTO_INCREMENT,
    username varchar(30),
    password varchar(64),
    first_name varchar(50),
    last_name varchar(50),
    date_of_birth date,
    CONSTRAINT pk_users PRIMARY KEY(user_id)
);

CREATE TABLE user_role(
    user_id int,
    role_id int,
    CONSTRAINT pk_user_role PRIMARY KEY(role_id, user_id),
    CONSTRAINT fk_users_roles FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_roles_users FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

CREATE TABLE authors(
    author_id int AUTO_INCREMENT,
    first_name varchar(50),
    last_name varchar(50),
    CONSTRAINT pk_authors PRIMARY KEY(author_id)
);

CREATE TABLE genres(
    genre_id int AUTO_INCREMENT,
    name varchar(50),
    description varchar(255),
    CONSTRAINT pk_genres PRIMARY KEY(genre_id)
);

CREATE TABLE book_details(
    book_id int AUTO_INCREMENT,
    isbn bigint(13),
    title varchar(255),
    release_date date,
    num_copies int,
    author_id int,
    genre_id int,
    CONSTRAINT pk_book_details PRIMARY KEY(book_id),
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES authors(author_id),
    CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES genres(genre_id)
);

CREATE TABLE book_copies(
    book_id int,
    copy_id int,
    CONSTRAINT pk_book_copies PRIMARY KEY (book_id, copy_id),
    CONSTRAINT fk_book_details FOREIGN KEY (book_id) REFERENCES book_details(book_id)
);

CREATE TABLE loans(
    book_id int,
    copy_id int,
    user_id int,
    due_date date,
    CONSTRAINT pk_loans PRIMARY KEY (book_id, copy_id, user_id),
    CONSTRAINT fk_users FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_book_copies FOREIGN KEY (book_id, copy_id) REFERENCES book_copies(book_id, copy_id)
);

CREATE TABLE reservations(
    res_id int AUTO_INCREMENT,
    book_id int,
    user_id int,
    date_reserved date,
    CONSTRAINT pk_reservations PRIMARY KEY(res_id),
    CONSTRAINT fk_user_reservations FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_book_details_reservations FOREIGN KEY (book_id) REFERENCES book_details(book_id)
);

INSERT INTO roles 
    VALUES(1, 'Admin', 'Has access to create, modify, and delete anything in the database');
INSERT INTO roles 
    VALUES(2, 'User', 'Has access to create, modify, and delete only data related to themselves');

INSERT INTO users 
    VALUES(1, 'librarian', '$2a$07$rJNj6GH5X8NHmRGICwF0lea3lY04dwoucIFagSvBkAhlLxwqxsbhe', 'Jane',
    'Doe', '1990-04-09');
INSERT INTO users 
    VALUES(2, 'user', '$2a$07$ho4YlKa4xTiRUriBlQq4u.eRMLSKuOhzj4zHV4jxf1xWNBHvTW9nW', 'John',
    'Doe', '1980-10-15');

INSERT INTO user_role 
    VALUES(1, 1);
INSERT INTO user_role 
    VALUES(2, 2);

INSERT INTO authors
    VALUES(1, "Charles", "Dickens");
INSERT INTO authors
    VALUES(2, "Leo", "Tolstoy");

INSERT INTO genres
    VALUES(1, "Fiction", "Literature that describes imaginary things and people");
INSERT INTO genres
    VALUES(2, "Historical Fiction", "Literature that describes imaginary things and people, but set in the past and can include real characters of the time period.");

INSERT INTO book_details
    VALUES(1, 9781548731854, "Great Expectations", "1861-01-01", 2, 1, 1);
INSERT INTO book_details
    VALUES(2, 9781977684233, "War and Peace", "1867-05-10", 3, 2, 2);

INSERT INTO book_copies
    VALUES(1, 1);
INSERT INTO book_copies
    VALUES(2, 1);
INSERT INTO book_copies
    VALUES(1, 2);
INSERT INTO book_copies
    VALUES(2, 2);

INSERT INTO loans
    VALUES(1, 1, 2, "2018-04-20");
INSERT INTO loans
    VALUES(2, 1, 2, "2018-04-20");
INSERT INTO reservations
    VALUES(1, 2, 2, "2018-04-21");