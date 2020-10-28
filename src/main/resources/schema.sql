
CREATE TABLE genres (
    id int(5) IDENTITY NOT NULL primary key,
    name varchar(50) DEFAULT NULL
);

CREATE TABLE authors (
    id bigint(20) IDENTITY NOT NULL primary key,
    first_name varchar(255) DEFAULT NULL,
    last_name varchar(255) DEFAULT NULL,
    year_of_birth int(4) DEFAULT NULL,
    year_of_death int(4) DEFAULT NULL
);

CREATE TABLE book_descriptions (
    id bigint(20) IDENTITY NOT NULL primary key,
    title varchar(255) DEFAULT NULL,
    isbn varchar(255) DEFAULT NULL,
    summary varchar(255) DEFAULT NULL,
    number_of_pages int,
    author_id bigint(20) DEFAULT NULL,
    genre_id int(5) DEFAULT NULL,
    image_url varchar(255) DEFAULT NULL,
    FOREIGN KEY(author_id) REFERENCES authors(id),
    FOREIGN KEY(genre_id) REFERENCES genres(id)
);

CREATE TABLE books (
    id bigint(20) IDENTITY NOT NULL primary key,
    available_for_loan boolean DEFAULT NULL,
    shelf varchar(5) DEFAULT NULL,
    book_description_id bigint(20) DEFAULT NULL,
    FOREIGN KEY(book_description_id) REFERENCES book_descriptions(id)
);

CREATE TABLE users (
    id bigint(20) IDENTITY NOT NULL primary key,
    type varchar(10) DEFAULT NULL,
    password varchar(255) DEFAULT NULL,
    first_name varchar(255) DEFAULT NULL,
    last_name varchar(255) DEFAULT NULL,
    email varchar(255) DEFAULT NULL,
    ssn varchar(12) DEFAULT NULL,
    street_address varchar(255) DEFAULT NULL,
    city varchar(255) DEFAULT NULL,
    zip_code int(5) DEFAULT NULL,
    country varchar(255) DEFAULT NULL
);

CREATE TABLE loans (
    id bigint(20) IDENTITY NOT NULL primary key,
    active boolean DEFAULT NULL,
    created_date date DEFAULT NULL,
    user_id bigint(20) DEFAULT NULL,
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE loan_items (
    id bigint(20) IDENTITY NOT NULL primary key,
    loan_date date DEFAULT NULL,
    due_date date DEFAULT NULL,
    returned_date date DEFAULT NULL,
    loan_id bigint(20) DEFAULT NULL,
    book_id bigint(20) DEFAULT NULL,
    FOREIGN KEY(loan_id) REFERENCES loans(id),
    FOREIGN KEY(book_id) REFERENCES books(id)
);




