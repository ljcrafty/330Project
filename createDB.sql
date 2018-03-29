USE Library;

DROP TABLE IF EXISTS priv_role;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS borrowed_book;
DROP TABLE IF EXISTS privileges;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS genres;

CREATE TABLE privileges(
    privilege_id int,
    name varchar(10),
    description varchar(255),
    CONSTRAINT pk_privileges PRIMARY KEY(privilege_id)
);

CREATE TABLE roles(
    role_id int,
    name varchar(10),
    description varchar(255),
    CONSTRAINT pk_roles PRIMARY KEY(role_id)
);

CREATE TABLE users(
    user_id int,
    username varchar(30),
    password varchar(50),
    first_name varchar(50),
    last_name varchar(50),
    age int,
    CONSTRAINT pk_users PRIMARY KEY(user_id)
);

CREATE TABLE authors(
    author_id int,
    first_name varchar(50),
    last_name varchar(50),
    CONSTRAINT pk_authors PRIMARY KEY(author_id)
);

CREATE TABLE genres(
    genre_id int,
    name varchar(50),
    description varchar(255),
    CONSTRAINT pk_genres PRIMARY KEY(genre_id)
);

CREATE TABLE books(
    book_id int,
    title varchar(255),
    release_date date,
    num_copies int,
    author_id int,
    genre_id int,
    CONSTRAINT pk_books PRIMARY KEY(book_id),
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES authors(author_id),
    CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES genres(genre_id)
);

CREATE TABLE priv_role(
    role_id int,
    privilege_id int,
    CONSTRAINT pk_priv_role PRIMARY KEY(role_id, privilege_id),
    CONSTRAINT fk_privileges FOREIGN KEY (privilege_id) REFERENCES privileges(privilege_id),
    CONSTRAINT fk_roles FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

CREATE TABLE user_role(
    user_id int,
    role_id int,
    CONSTRAINT pk_user_role PRIMARY KEY(role_id, user_id),
    CONSTRAINT fk_users_roles FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_roles_users FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

CREATE TABLE borrowed_book(
    book_id int,
    user_id int,
    reserved boolean,
    CONSTRAINT pk_borrowed_book PRIMARY KEY(book_id, user_id),
    CONSTRAINT fk_users FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_books FOREIGN KEY (book_id) REFERENCES books(book_id)
);