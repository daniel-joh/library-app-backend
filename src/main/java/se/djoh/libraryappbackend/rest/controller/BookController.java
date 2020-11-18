package se.djoh.libraryappbackend.rest.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.djoh.libraryappbackend.domain.Book;
import se.djoh.libraryappbackend.domain.BookDescription;
import se.djoh.libraryappbackend.rest.dto.BookDto;
import se.djoh.libraryappbackend.rest.exception.ResourceNotFoundException;
import se.djoh.libraryappbackend.service.BookService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@Log
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping(path = "/books")
    public List<BookDto> getBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String author, @RequestParam(required = false) String isbn,
                                  @RequestParam(required = false) String genre) {
        List<BookDescription> bookDescriptions = bookService.searchByCriteria(title, author, isbn, genre);

        if (bookDescriptions == null) {
            throw new ResourceNotFoundException("No books found!");
        }
        return createBookDtos(bookDescriptions);
    }

    @GetMapping(path = "/books/{id}")
    public BookDto getBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id);

        if (book == null) {
            throw new ResourceNotFoundException("Book not found!");
        }

        BookDto dto = BookDto.createBookDto(book.getBookDescription());
        return BookDto.addBookInformationToDto(dto, book);
    }

    private List<BookDto> createBookDtos(List<BookDescription> bookDescriptions) {
        List<BookDto> bookDtos = new ArrayList<>();
        bookDescriptions.forEach(bookDescription -> {
            List<BookDto> books = BookDto.createBookDtoList(bookDescription);
            bookDtos.addAll(books);
        });
        return bookDtos;
    }

}
