CREATE TABLE if not exists genres (
    id int(5) IDENTITY NOT NULL primary key,
    name varchar(50) DEFAULT NULL
);

CREATE TABLE if not exists authors (
    id bigint(20) IDENTITY NOT NULL primary key,
    first_name varchar(255) DEFAULT NULL,
    last_name varchar(255) DEFAULT NULL,
    year_of_birth int(4) DEFAULT NULL,
    year_of_death int(4) DEFAULT NULL
);

CREATE TABLE if not exists publishers (
    id bigint(20) IDENTITY NOT NULL primary key,
    name varchar(50) NOT NULL
);

CREATE TABLE if not exists book_descriptions (
    id bigint(20) IDENTITY NOT NULL primary key,
    title varchar(255) DEFAULT NULL,
    isbn varchar(255) DEFAULT NULL,
    summary varchar(255) DEFAULT NULL,
    language varchar(15) DEFAULT NULL,
    number_of_pages int,
    author_id bigint(20) DEFAULT NULL,
    genre_id int(5) DEFAULT NULL,
    image_url varchar(255) DEFAULT NULL,
    published_year int(4) DEFAULT NULL,
    format varchar(20) DEFAULT NULL,
    publisher_id bigint(20) DEFAULT NULL,
    FOREIGN KEY(publisher_id) REFERENCES publishers(id),
    FOREIGN KEY(author_id) REFERENCES authors(id),
    FOREIGN KEY(genre_id) REFERENCES genres(id)
);

CREATE TABLE if not exists books (
    id bigint(20) IDENTITY NOT NULL primary key,
    available_for_loan boolean DEFAULT NULL,
    shelf varchar(5) DEFAULT NULL,
    book_description_id bigint(20) DEFAULT NULL,
    FOREIGN KEY(book_description_id) REFERENCES book_descriptions(id)
);

CREATE TABLE if not exists users (
    id bigint(20) IDENTITY NOT NULL primary key,
    username varchar(50) NOT NULL,
    password varchar(255) NOT NULL,
    first_name varchar(255) DEFAULT NULL,
    last_name varchar(255) DEFAULT NULL,
    email varchar(255) DEFAULT NULL,
    ssn varchar(12) DEFAULT NULL,
    phone_number varchar(20) DEFAULT NULL,
    street_address varchar(255) DEFAULT NULL,
    city varchar(255) DEFAULT NULL,
    zip_code int(5) DEFAULT NULL,
    country varchar(255) DEFAULT NULL,
    account_non_expired boolean DEFAULT true,
    account_non_locked boolean DEFAULT true,
    credentials_non_expired boolean DEFAULT true,
    enabled boolean DEFAULT true
);

CREATE TABLE if not exists roles (
    id bigint(20) IDENTITY NOT NULL primary key,
    role varchar(20) DEFAULT NULL
);

CREATE TABLE if not exists user_roles (
    user_id bigint(20) NOT NULL,
    role_id bigint(20) NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(role_id) REFERENCES roles(id)
);

CREATE TABLE if not exists loans (
    id bigint(20) IDENTITY NOT NULL primary key,
    active boolean DEFAULT NULL,
    created_date date DEFAULT NULL,
    user_id bigint(20) DEFAULT NULL,
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE if not exists loan_items (
    id bigint(20) IDENTITY NOT NULL primary key,
    loan_date date DEFAULT NULL,
    due_date date DEFAULT NULL,
    returned_date date DEFAULT NULL,
    loan_id bigint(20) DEFAULT NULL,
    book_id bigint(20) DEFAULT NULL,
    FOREIGN KEY(loan_id) REFERENCES loans(id),
    FOREIGN KEY(book_id) REFERENCES books(id)
);





