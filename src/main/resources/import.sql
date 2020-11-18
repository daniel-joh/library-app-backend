insert into authors (id, first_name, last_name, year_of_birth, year_of_death) values (1, 'Stephen', 'King', 1950, NULL);
insert into authors (id, first_name, last_name, year_of_birth, year_of_death) values (2, 'Tom', 'Clancy', 1945, NULL);

insert into genres (id, name) values (1, 'Thriller');
insert into genres (id, name) values (2, 'Fantasy');
insert into genres (id, name) values (3, 'Crime');

insert into publishers (id, name) values (1, 'Albert Bonniers Förlag');
insert into publishers (id, name) values (2, 'Bra Böcker');

insert into book_descriptions (id, title, isbn, summary, number_of_pages, author_id, genre_id, image_url, language, format, published_year, publisher_id) values (1, 'Pestens tid', 123456, 'en sammanfattning', 900, 1, 1, 'images/books/king.jpg', 'Swedish', 'PAPERBACK', 1980, 1);
insert into book_descriptions (id, title, isbn, summary, number_of_pages, author_id, genre_id, image_url, language, format, published_year, publisher_id) values (2, 'Det', 123789, 'en sammanfattning', 1000, 1, 1, 'images/books/king.jpg', 'Swedish','HARDCOVER', 1983, 1);
insert into book_descriptions (id, title, isbn, summary, number_of_pages, author_id, genre_id, image_url, language, format, published_year, publisher_id) values (3, 'Christine', 156756, 'en sammanfattning', 800, 1, 1, 'images/books/king.jpg', 'Swedish', 'HARDCOVER', 1985, 1);
insert into book_descriptions (id, title, isbn, summary, number_of_pages, author_id, genre_id, image_url, language, format, published_year, publisher_id) values (4, 'Lida', 156756, 'en sammanfattning', 500, 1, 1, 'images/books/king.jpg', 'Swedish','PAPERBACK', 1984, 1);
insert into book_descriptions (id, title, isbn, summary, number_of_pages, author_id, genre_id, image_url, language, format, published_year, publisher_id) values (5, 'Varsel', 156756, 'en sammanfattning', 600, 1, 1, 'images/books/king.jpg', 'Swedish','PAPERBACK', 1980, 1);
insert into book_descriptions (id, title, isbn, summary, number_of_pages, author_id, genre_id, image_url, language, format, published_year, publisher_id) values (6, 'Jakten på röd oktober', 345456, 'en sammanfattning', 950, 2, 1, 'images/books/king.jpg', 'Swedish','HARDCOVER', 1990, 2);

insert into books (id, available_for_loan, shelf, book_description_id) values (1, false, '1E', 1);
insert into books (id, available_for_loan, shelf, book_description_id) values (2, false, '1E', 2);
insert into books (id, available_for_loan, shelf, book_description_id) values (3, true, '1E', 3);
insert into books (id, available_for_loan, shelf, book_description_id) values (4, true, '1E', 4);
insert into books (id, available_for_loan, shelf, book_description_id) values (5, false, '1E', 1);
insert into books (id, available_for_loan, shelf, book_description_id) values (6, true, '1E', 5);
insert into books (id, available_for_loan, shelf, book_description_id) values (7, true, '1E', 6);
insert into books (id, available_for_loan, shelf, book_description_id) values (8, true, '1E', 6);

insert into users (id, username, password, first_name, last_name, email, ssn, phone_number, street_address, city,zip_code, country, account_non_expired, account_non_locked, credentials_non_expired, enabled) values (1, 'admin', 'password', 'Admin', 'Admin', 'admin@mail.com', '196001100123','08-111111', 'Gatan 1','Uppsala', 17895, 'Sverige', true, true, true, true);
insert into users (id, username, password, first_name, last_name, email, ssn, phone_number, street_address, city,zip_code, country, account_non_expired, account_non_locked, credentials_non_expired, enabled) values (2, 'user', 'password', 'User', 'User', 'user@mail.com', '197502010156', '08-222222', 'Vägen 1','Stockholm', 12345, 'Sverige', true, true, true, true);

insert into roles (id, role) values (1, 'ROLE_ADMIN');
insert into roles (id, role) values (2, 'ROLE_USER');

insert into user_roles (user_id, role_id) values (1,1);
insert into user_roles (user_id, role_id) values (2,2);








