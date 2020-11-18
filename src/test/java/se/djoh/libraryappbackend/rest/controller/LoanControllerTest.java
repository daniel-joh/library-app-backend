package se.djoh.libraryappbackend.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import se.djoh.libraryappbackend.domain.Book;
import se.djoh.libraryappbackend.domain.Loan;
import se.djoh.libraryappbackend.domain.LoanItem;
import se.djoh.libraryappbackend.domain.User;
import se.djoh.libraryappbackend.rest.dto.LoanCartDto;
import se.djoh.libraryappbackend.rest.dto.LoanDto;
import se.djoh.libraryappbackend.rest.dto.LoanedBookDto;
import se.djoh.libraryappbackend.service.LoanService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class LoanControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private LoanService loanService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void creatingLoanWithCorrectInputWithAuthShouldResultInActiveLoanCreatedTest() throws Exception {
        Long userId = 1L;
        LoanCartDto dto = new LoanCartDto();
        dto.setUserId(userId);
        List<Long> bookIds = new ArrayList<>();
        bookIds.add(1L);
        bookIds.add(2L);
        dto.setBookIds(bookIds);

        Loan loan = createLoan();

        when(loanService.createLoan(userId, bookIds)).thenReturn(loan);

        String postValue = OBJECT_MAPPER.writeValueAsString(dto);

        MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/loans")
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(postValue))
                .andExpect(status().isCreated())
                .andReturn();

        LoanDto loanDto = OBJECT_MAPPER.readValue(storyResult.getResponse().getContentAsString(), LoanDto.class);

        assertTrue(loanDto.isActive());
        assertEquals(2, loanDto.getLoanedBooks().size());
    }

    @Test
    public void creatingLoanWithNoBooksWithAuthShouldResultIn400StatusTest() throws Exception {
        Long userId = 1L;
        LoanCartDto dto = new LoanCartDto();
        dto.setUserId(userId);

        String postValue = OBJECT_MAPPER.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/loans")
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(postValue))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void creatingLoanWithNoUserIdWithAuthShouldResultIn400StatusTest() throws Exception {
        LoanCartDto dto = new LoanCartDto();

        String postValue = OBJECT_MAPPER.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/loans")
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(postValue))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void creatingLoanWithCorrectInputWithNoAuthShouldResultIn401StatusTest() throws Exception {
        Long userId = 1L;
        LoanCartDto dto = new LoanCartDto();
        dto.setUserId(userId);
        List<Long> bookIds = new ArrayList<>();
        bookIds.add(1L);
        bookIds.add(2L);
        dto.setBookIds(bookIds);

        Loan loan = createLoan();

        when(loanService.createLoan(userId, bookIds)).thenReturn(loan);

        String postValue = OBJECT_MAPPER.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postValue))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void returningBooksWithCorrectInputWithAuthShouldResultInReturnedBookTest() throws Exception {
        Long bookId = 1L;
        Loan loan = createLoan();

        when(loanService.returnLoanedBook(bookId)).thenReturn(loan);

        MvcResult storyResult = mockMvc.perform(patch("/api/loans/return/" + bookId)
                .with(httpBasic("user", "user")))
                .andExpect(status().isOk())
                .andReturn();
        LoanDto loanDto = OBJECT_MAPPER.readValue(storyResult.getResponse().getContentAsString(), LoanDto.class);

        List<LoanedBookDto> bookDto = loanDto.getLoanedBooks().stream()
                .filter(b -> b.getBookId() == bookId).collect(Collectors.toList());

        assertNotNull(bookDto.get(0).getReturnedDate());
    }

    @Test
    public void returningBooksWithIncorrectInputWithAuthShouldResultIn400StatusTest() throws Exception {
        String text = "123asdf";
        MvcResult storyResult = mockMvc.perform(patch("/api/loans/return/" + text)
                .with(httpBasic("user", "user")))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void returningBooksWithCorrectInputWithNoAuthShouldResultIn401StatusTest() throws Exception {
        Long bookId = 1L;
        Loan loan = createLoan();

        when(loanService.returnLoanedBook(bookId)).thenReturn(loan);

        mockMvc.perform(patch("/api/loans/return/" + bookId))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void gettingLoansWithAuthSuccessfullyTest() throws Exception {
        Long userId = 1L;
        Loan loan = createLoan();
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);

        when(loanService.getLoans(userId)).thenReturn(loans);

        MvcResult storyResult = mockMvc.perform(get("/api/loans?userId=" + userId)
                .with(httpBasic("user", "user")))
                .andExpect(status().isOk())
                .andReturn();

        List<LoanDto> loanDtos = OBJECT_MAPPER.readValue(storyResult.getResponse().getContentAsString(),
                new TypeReference<List<LoanDto>>() {
                });

        assertEquals(1, loanDtos.size());
    }

    @Test
    public void gettingLoansWithIncorrectInputWithAuthShouldResultIn400StatusTest() throws Exception {
        String text = "123asdf";
        mockMvc.perform(get("/api/loans?userId=" + text)
                .with(httpBasic("user", "user")))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void gettingLoansWithNoAuthShouldResultIn401StatusTest() throws Exception {
        Long userId = 1L;
        Loan loan = createLoan();
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);

        when(loanService.getLoans(userId)).thenReturn(loans);

        mockMvc.perform(get("/api/loans?userId=" + userId))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void gettingLoanWithAuthSuccessfullyTest() throws Exception {
        Long loanId = 1L;
        Long userId = 1L;
        Loan loan = createLoan();

        when(loanService.getLoanByUserIdAndLoanId(userId, loanId)).thenReturn(loan);

        MvcResult storyResult = mockMvc.perform(get("/api/loans/" + loanId + "?userId=" + userId)
                .with(httpBasic("user", "user")))
                .andExpect(status().isOk())
                .andReturn();

        LoanDto loanDto = OBJECT_MAPPER.readValue(storyResult.getResponse().getContentAsString(), LoanDto.class);

        assertEquals(loanId, loanDto.getLoanId());
    }

    @Test
    public void gettingNonExistingLoanWithAuthShouldResultIn404StatusTest() throws Exception {
        Long userId = 1L;
        Long loanId = 9999L;
        mockMvc.perform(get("/api/loans/" + loanId + "?userId=" + userId)
                .with(httpBasic("user", "user")))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void gettingLoanWithNoAuthShouldResultIn401StatusTest() throws Exception {
        Long loanId = 1L;
        Long userId = 1L;

        mockMvc.perform(get("/api/loans/" + loanId + "?userId=" + userId))
                .andExpect(status().isUnauthorized());
    }

    private Loan createLoan() {
        User user = new User();
        user.setId(1L);

        Book book = new Book();
        book.setId(1L);
        Book book2 = new Book();
        book2.setId(2L);

        Loan loan = new Loan();
        loan.setActive(true);
        loan.setId(1L);
        loan.setUser(user);

        LoanItem loanItem = new LoanItem();
        loanItem.setReturnedDate(now());
        loanItem.setBook(book);

        LoanItem loanItem2 = new LoanItem();
        loanItem2.setReturnedDate(now());
        loanItem2.setBook(book2);

        List<LoanItem> loanItems = new ArrayList<>();
        loanItems.add(loanItem);
        loanItems.add(loanItem2);
        loan.setLoanItems(loanItems);
        return loan;
    }

}
