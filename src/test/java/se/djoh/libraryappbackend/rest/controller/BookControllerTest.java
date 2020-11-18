package se.djoh.libraryappbackend.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import se.djoh.libraryappbackend.domain.Book;
import se.djoh.libraryappbackend.domain.BookDescription;
import se.djoh.libraryappbackend.rest.dto.BookDto;
import se.djoh.libraryappbackend.service.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class BookControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void gettingBooksSuccessfullyWithAuthTest() throws Exception {
        List<BookDescription> bookDescriptions = createBookDescriptions();

        when(bookService.searchByCriteria(anyString(), anyString(), anyString(), anyString())).thenReturn(bookDescriptions);

        MvcResult storyResult = mockMvc.perform(get("/api/books?title=titeln&author=författaren&isbn=12345&genre=genren")
                .with(httpBasic("user", "user")))
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> bookDtos = OBJECT_MAPPER.readValue(storyResult.getResponse().getContentAsString(),
                new TypeReference<List<BookDto>>() {
                });

        assertEquals(2, bookDtos.size());
    }

    @Test
    public void gettingBooksWithEmptySearchParamsWithAuthShouldResultIn404StatusTest() throws Exception {
        when(bookService.searchByCriteria(anyString(), anyString(), anyString(), anyString())).thenReturn(null);
        mockMvc.perform(get("/api/books?title=&author=&isbn=&genre=")
                .with(httpBasic("user", "user")))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void gettingBooksWithNoAuthShouldResultIn401StatusTest() throws Exception {
        when(bookService.searchByCriteria(anyString(), anyString(), anyString(), anyString())).thenReturn(null);
        mockMvc.perform(get("/api/books?title=titeln&author=författaren&isbn=12345&genre=genren"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void gettingBookSuccessfullyWithAuthTest() throws Exception {
        Book book = new Book();
        book.setId(1L);
        BookDescription bookDesc = new BookDescription();
        bookDesc.setId(1L);
        book.setBookDescription(bookDesc);

        when(bookService.getBookById(anyLong())).thenReturn(book);

        MvcResult storyResult = mockMvc.perform(get("/api/books/1")
                .with(httpBasic("user", "user")))
                .andExpect(status().isOk())
                .andReturn();

        BookDto retBook = OBJECT_MAPPER.readValue(storyResult.getResponse().getContentAsString(), BookDto.class);

        assertNotNull(retBook);
        assertEquals(1, retBook.getBookId());
    }

    @Test
    public void gettingBookWithNoAuthShouldResultIn401StatusTest() throws Exception {
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void gettingBookWithNoAvailableBookWithAuthShouldResultIn404StatusTest() throws Exception {
        when(bookService.getBookById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/books/1")
                .with(httpBasic("user", "user")))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    private List<BookDescription> createBookDescriptions() {
        List<BookDescription> bookDescriptions = new ArrayList<>();
        BookDescription bookDescription = new BookDescription();
        bookDescription.setId(1L);

        BookDescription bookDescription2 = new BookDescription();
        bookDescription2.setId(2L);

        Book book = new Book();
        book.setId(1L);
        Book book2 = new Book();
        book2.setId(2L);

        List<Book> books = new ArrayList<>();
        books.add(book);
        List<Book> books2 = new ArrayList<>();
        books2.add(book2);
        bookDescription.setBooks(books);
        bookDescription2.setBooks(books2);

        bookDescriptions.add(bookDescription);
        bookDescriptions.add(bookDescription2);
        return bookDescriptions;
    }

}
