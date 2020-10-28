package se.djoh.libraryappbackend.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import se.djoh.libraryappbackend.domain.Book;
import se.djoh.libraryappbackend.domain.BookDescription;
import se.djoh.libraryappbackend.rest.dto.BookDto;
import se.djoh.libraryappbackend.service.BookService;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void gettingBooksSuccessfullyTest() throws Exception {
        List<BookDescription> bookDescriptions = createBookDescriptions();

        when(bookService.searchByCriteria(anyString(), anyString(), anyString(), anyString())).thenReturn(bookDescriptions);

        MvcResult storyResult = mockMvc.perform(get("/api/books?title=titeln&author=författaren&isbn=12345&genre=genren"))
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> bookDtos = OBJECT_MAPPER.readValue(storyResult.getResponse().getContentAsString(),
                new TypeReference<List<BookDto>>(){});

        assertEquals(2, bookDtos.size());
    }

    @Test
    public void gettingBooksWithEmptySearchParamsShouldResultIn404StatusTest() throws Exception {
        when(bookService.searchByCriteria(anyString(), anyString(), anyString(), anyString())).thenReturn(null);
        mockMvc.perform(get("/api/books?title=&author=&isbn=&genre="))
                .andExpect(status().isNotFound())
                .andDo(print())
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
