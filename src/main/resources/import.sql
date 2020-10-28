insert into authors (id, first_name, last_name, year_of_birth, year_of_death) values (1, 'Stephen', 'King', 1950, NULL);
insert into authors (id, first_name, last_name, year_of_birth, year_of_death) values (2, 'Tom', 'Clancy', 1945, NULL);

insert into genres (id, name) values (1, 'Thriller');
insert into genres (id, name) values (2, 'Fantasy');
insert into genres (id, name) values (3, 'Crime');

insert into book_descriptions (id, title, isbn, summary, number_of_pages, author_id, genre_id, image_url) values (1, 'Pestens tid', 123456, 'en sammanfattning', 900, 1, 1, 'images/books/king.jpg');
insert into book_descriptions (id, title, isbn, summary, number_of_pages, author_id, genre_id, image_url) values (2, 'Det', 123789, 'en sammanfattning', 1000, 1, 1, 'images/books/king.jpg');
insert into book_descriptions (id, title, isbn, summary, number_of_pages, author_id, genre_id, image_url) values (3, 'Christine', 156756, 'en sammanfattning', 800, 1, 1, 'images/books/king.jpg');
insert into book_descriptions (id, title, isbn, summary, number_of_pages, author_id, genre_id, image_url) values (4, 'Lida', 156756, 'en sammanfattning', 500, 1, 1, 'images/books/king.jpg');
insert into book_descriptions (id, title, isbn, summary, number_of_pages, author_id, genre_id, image_url) values (5, 'Varsel', 156756, 'en sammanfattning', 600, 1, 1, 'images/books/king.jpg');
insert into book_descriptions (id, title, isbn, summary, number_of_pages, author_id, genre_id, image_url) values (6, 'Jakten på röd oktober', 345456, 'en sammanfattning', 950, 2, 1, 'images/books/king.jpg');

insert into books (id, available_for_loan, shelf, book_description_id) values (1, false, '1E', 1);
insert into books (id, available_for_loan, shelf, book_description_id) values (2, false, '1E', 2);
insert into books (id, available_for_loan, shelf, book_description_id) values (3, true, '1E', 3);
insert into books (id, available_for_loan, shelf, book_description_id) values (4, true, '1E', 4);
insert into books (id, available_for_loan, shelf, book_description_id) values (5, false, '1E', 1);
insert into books (id, available_for_loan, shelf, book_description_id) values (6, true, '1E', 5);
insert into books (id, available_for_loan, shelf, book_description_id) values (7, true, '1E', 6);
insert into books (id, available_for_loan, shelf, book_description_id) values (8, true, '1E', 6);

 insert into users (id, type, password, first_name, last_name, email, ssn, street_address, city,zip_code, country) values (1, 'STANDARD', 'password', 'Per', 'Nilsson', 'per.nilsson@hotmail.com', '196001100123', 'Gatan 1', 'Uppsala', 17895, 'Sverige');
 insert into users (id, type, password, first_name, last_name, email, ssn, street_address, city, zip_code, country) values (2, 'STANDARD', 'password', 'Nils', 'Persson', 'nils.persson@hotmail.com', '197502010156', 'Vägen 1','Stockholm', 12345, 'Sverige');

insert into loans (id, active, created_date, user_id) values (1, true,'2020-10-01', 1);
insert into loans (id, active, created_date, user_id) values (2, true,'2020-10-01', 1);

insert into loan_items (id, loan_date, due_date, returned_date, loan_id, book_id) values (1, '2020-10-01', '2020-11-01', NULL, 1, 1);

insert into loan_items (id, loan_date, due_date, returned_date, loan_id, book_id) values (2, '2020-10-05', '2020-11-05', NULL, 2, 2);








