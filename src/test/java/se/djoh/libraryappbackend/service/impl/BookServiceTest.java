package se.djoh.libraryappbackend.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import se.djoh.libraryappbackend.domain.Book;
import se.djoh.libraryappbackend.repository.BookRepository;
import se.djoh.libraryappbackend.service.BookService;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
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

        long id = returnedBook.getId();
        assertEquals(1, id);
    }

    @Test
    public void gettingBookByIdWithNoFoundBookTest() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(null));

        Book returnedBook = bookService.getBookById(bookId);

        assertNull(returnedBook);
    }

}
