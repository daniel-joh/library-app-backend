package se.djoh.libraryappbackend.service;

import se.djoh.libraryappbackend.domain.Loan;

import java.util.List;

public interface LoanService {
    Loan createLoan(Long userId, List<Long> bookIds);

    List<Loan> getLoans(Long userId);

    Loan getLoanById(Long loanId);

    Loan returnLoanedBook(Long bookId);

}
