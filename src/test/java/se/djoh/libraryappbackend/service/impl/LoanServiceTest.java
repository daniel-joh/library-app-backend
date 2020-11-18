package se.djoh.libraryappbackend.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import se.djoh.libraryappbackend.domain.Book;
import se.djoh.libraryappbackend.domain.Loan;
import se.djoh.libraryappbackend.domain.LoanItem;
import se.djoh.libraryappbackend.domain.User;
import se.djoh.libraryappbackend.repository.LoanItemRepository;
import se.djoh.libraryappbackend.repository.LoanRepository;
import se.djoh.libraryappbackend.service.BookService;
import se.djoh.libraryappbackend.service.LoanService;
import se.djoh.libraryappbackend.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class LoanServiceTest {
    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanItemRepository loanItemRepository;

    @Mock
    private UserService userService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private LoanService loanService = new LoanServiceImpl();

    @Test
    public void gettingLoanByIdSuccessfulTest() {
        Long loanId = 1L;
        Long userId = 1L;
        Loan loan = new Loan();
        loan.setId(loanId);

        when(loanRepository.findLoanByUserIdAndId(userId, loanId)).thenReturn(loan);

        Loan retLoan = loanService.getLoanByUserIdAndLoanId(userId, loanId);

        long id = retLoan.getId();
        assertEquals(1, id);
    }

    @Test
    public void gettingLoanByNullIdShouldResultInNullTest() {
        Loan retLoan = loanService.getLoanByUserIdAndLoanId(null, null);

        assertNull(retLoan);
    }

    @Test
    public void gettingLoansByUserIdSuccessfulTest() {
        Long userId = 1L;
        Loan loan = new Loan();
        loan.setId(1L);
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);

        when(loanRepository.findLoansByUserId(1L)).thenReturn(loans);

        List<Loan> retLoans = loanService.getLoans(userId);

        assertEquals(1, retLoans.size());
    }

    @Test
    public void gettingLoansByNullUserIdShouldResultInNoFoundLoansTest() {
        List<Loan> retLoans = loanService.getLoans(null);

        assertEquals(0, retLoans.size());
    }

    @Test
    public void createLoanSuccessfulTest() {
        Long userId = 1L;
        List<Long> bookIds = new ArrayList<>();
        bookIds.add(1L);

        Book book = new Book();
        book.setId(1L);

        Loan loan = createLoan();
        List<LoanItem> loanItems = loan.getLoanItems();

        when(userService.getUserById(userId)).thenReturn(new User());
        when(bookService.getBookById(1L)).thenReturn(book);
        when(loanRepository.save(any())).thenReturn(loan);
        when(loanItemRepository.save(any())).thenReturn(loanItems.get(0));

        Loan retLoan = loanService.createLoan(userId, bookIds);

        long loanId = retLoan.getId();
        long bookId = retLoan.getLoanItems().get(0).getBook().getId();
        assertEquals(1, loanId);
        assertEquals(1, bookId);
    }

    @Test
    public void createLoanShouldReturnNullIfCalledWithNoBookIdsTest() {
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(new User());

        Loan retLoan = loanService.createLoan(userId, new ArrayList<>());

        assertNull(retLoan);
    }

    @Test
    public void returnBookSuccessfulTest() {
        Long bookId = 1L;

        Loan loan = createLoan();

        when(bookService.getBookById(bookId)).thenReturn(loan.getLoanItems().get(0).getBook());
        when(loanRepository.save(any())).thenReturn(loan);

        Loan retLoan = loanService.returnLoanedBook(bookId);

        assertNotNull(retLoan.getLoanItems().get(0).getReturnedDate());
        assertFalse(retLoan.isActive());
    }

    @Test
    public void returnBookFailTest() {
        Loan retLoan = loanService.returnLoanedBook(null);

        assertNull(retLoan);
    }


    private Loan createLoan() {
        Long loanId = 1L;
        Loan loan = new Loan();
        loan.setId(loanId);
        loan.setUser(new User());
        loan.setActive(true);
        loan.setCreatedDate(LocalDate.now());

        Book book = new Book();
        book.setId(1L);

        LoanItem loanItem = new LoanItem();
        loanItem.setLoan(loan);
        loanItem.setBook(book);
        book.setLoanItem(loanItem);
        List<LoanItem> loanItems = new ArrayList<>();
        loanItems.add(loanItem);
        loan.setLoanItems(loanItems);

        return loan;
    }
}
