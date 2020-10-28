package se.djoh.libraryappbackend.service;

import se.djoh.libraryappbackend.domain.Book;
import se.djoh.libraryappbackend.domain.BookDescription;
import se.djoh.libraryappbackend.rest.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDescription> searchByCriteria(String title, String author, String isbn, String genre);

    Book getBookById(Long id);

}
