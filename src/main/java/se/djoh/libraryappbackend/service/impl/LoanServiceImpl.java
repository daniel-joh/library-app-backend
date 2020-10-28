package se.djoh.libraryappbackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanItemRepository loanItemRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    private static int LOAN_NUMBER_OF_DAYS = 30;        //number of days a loan is valid

    @Override
    @Transactional
    public Loan createLoan(Long userId, List<Long> bookIds) {
        if (userId == null || bookIds == null || bookIds.isEmpty()) {
            return null;
        }
        Loan loan = new Loan();
        loan.setActive(true);
        loan.setCreatedDate(LocalDate.now());
        User user = userService.getUserById(userId);
        user.addLoan(loan);

        for (Long bookId : bookIds) {
            Book book = bookService.getBookById(bookId);
            book.setAvailableForLoan(false);

            LoanItem loanItem = new LoanItem();
            loanItem.setBook(book);
            book.setLoanItem(loanItem);
            loanItem.setLoanDate(LocalDate.now());
            loanItem.setDueDate(LocalDate.now().plusDays(LOAN_NUMBER_OF_DAYS));
            loanItem = loanItemRepository.save(loanItem);
            loan.addLoanItem(loanItem);

        }
        return loanRepository.save(loan);
    }

    @Override
    public List<Loan> getLoans(Long userId) {
        return loanRepository.findLoansByUserId(userId);
    }

    @Override
    public Loan getLoanById(Long loanId) {
        Optional<Loan> loan = loanRepository.findById(loanId);
        return loan.orElse(null);
    }

    @Override
    @Transactional
    public Loan returnLoanedBook(Long bookId) {
        if (bookId == null) {
            return null;
        }
        Book book = bookService.getBookById(bookId);

        if (book == null) {
            return null;
        }

        book.setAvailableForLoan(true);
        book.getLoanItem().setReturnedDate(LocalDate.now());

        Loan loan = book.getLoanItem().getLoan();

        //loan should be inactivated if all loanitems has been returned
        boolean loanItemHasNotBeenReturned = false;
        if (loan.isActive()) {
            for (LoanItem loanItem : loan.getLoanItems()) {
                if (loanItem.getReturnedDate() == null) {
                    loanItemHasNotBeenReturned = true;
                    break;
                }
            }
        }

        if (!loanItemHasNotBeenReturned) {
            loan.setActive(false);
        }

        return loanRepository.save(loan);
    }

}
