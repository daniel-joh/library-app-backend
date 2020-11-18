package se.djoh.libraryappbackend.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import se.djoh.libraryappbackend.domain.BookDescription;
import se.djoh.libraryappbackend.service.BookService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@Sql({"/testdata.sql"})
public class BookServiceRealDbTest {
    @Autowired
    private BookService bookService;

    @Test
    public void gettingBooksByTitleAuthorIsbnAndGenreTest() {
        List<BookDescription> books = bookService.searchByCriteria("Pestens tid", "Stephen King", "123456", "Thriller");

        assertEquals(1, books.size());
    }

    @Test
    public void gettingBooksByTitleAuthorIsbnTest() {
        List<BookDescription> books = bookService.searchByCriteria("Pestens tid", "Stephen King", "123456", "");

        assertEquals(1, books.size());
    }

    @Test
    public void gettingBooksByTitleAuthorTest() {
        List<BookDescription> books = bookService.searchByCriteria("Pestens tid", "Stephen King", "", "");

        assertEquals(1, books.size());
    }

    @Test
    public void gettingBooksByTitleTest() {
        List<BookDescription> books = bookService.searchByCriteria("Pestens tid", "", "", "");

        assertEquals(1, books.size());
    }

    @Test
    public void gettingBooksByAuthorTest() {
        List<BookDescription> books = bookService.searchByCriteria("", "Stephen King", "", "");

        assertEquals(5, books.size());
    }

    @Test
    public void gettingBooksByIsbnTest() {
        List<BookDescription> books = bookService.searchByCriteria("", "", "123456", "");

        assertEquals(1, books.size());
    }

    @Test
    public void gettingBooksByGenreTest() {
        List<BookDescription> books = bookService.searchByCriteria("", "", "", "Thriller");

        assertEquals(6, books.size());
    }

    @Test
    public void gettingBooksByAuthorIsbnTest() {
        List<BookDescription> books = bookService.searchByCriteria("", "Stephen King", "123456", "");

        assertEquals(1, books.size());
    }

    @Test
    public void gettingBooksByIsbnGenreTest() {
        List<BookDescription> books = bookService.searchByCriteria("", "", "123456", "Thriller");

        assertEquals(1, books.size());
    }

    @Test
    public void gettingBooksByTitleIsbnTest() {
        List<BookDescription> books = bookService.searchByCriteria("Pestens tid", "", "123456", "");

        assertEquals(1, books.size());
    }

    @Test
    public void gettingBooksByAuthorGenreTest() {
        List<BookDescription> books = bookService.searchByCriteria("", "Stephen King", "", "Thriller");

        assertEquals(5, books.size());
    }

    @Test
    public void gettingBooksByTitleGenreTest() {
        List<BookDescription> books = bookService.searchByCriteria("Pestens tid", "", "", "Thriller");

        assertEquals(1, books.size());
    }

    @Test
    public void gettingBooksByEmptyParametersShouldResultInNullTest() {
        List<BookDescription> books = bookService.searchByCriteria("", "", "", "");

        assertNull(books);
    }


}
