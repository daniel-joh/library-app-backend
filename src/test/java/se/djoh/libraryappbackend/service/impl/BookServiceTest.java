package se.djoh.libraryappbackend.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import se.djoh.libraryappbackend.domain.Book;
import se.djoh.libraryappbackend.repository.BookRepository;
import se.djoh.libraryappbackend.service.BookService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService = new BookServiceImpl();

    @Test
    public void gettingBookByIdSuccessfulTest() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Book returnedBook = bookService.getBookById(bookId);

        assertEquals(1, returnedBook.getId());
    }

    @Test
    public void gettingBookByIdWithNoFoundBookTest() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(null));

        Book returnedBook = bookService.getBookById(bookId);

        assertNull(returnedBook);
    }

}
