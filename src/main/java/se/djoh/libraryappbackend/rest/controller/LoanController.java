package se.djoh.libraryappbackend.rest.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.djoh.libraryappbackend.domain.Loan;
import se.djoh.libraryappbackend.rest.dto.LoanCartDto;
import se.djoh.libraryappbackend.rest.dto.LoanDto;
import se.djoh.libraryappbackend.rest.exception.ResourceNotCreatedException;
import se.djoh.libraryappbackend.rest.exception.ResourceNotFoundException;
import se.djoh.libraryappbackend.service.LoanService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@Log
public class LoanController {
    @Autowired
    private LoanService loanService;

    @PostMapping(path = "/loans")
    public ResponseEntity<LoanDto> createLoan(@RequestBody @Valid LoanCartDto loanCartDto) {
        Loan loan = loanService.createLoan(loanCartDto.getUserId(), loanCartDto.getBookIds());

        if (loan == null) {
            throw new ResourceNotCreatedException("Loan not created!");
        }

        LoanDto loanDto = new LoanDto();
        loanDto = loanDto.createLoanDto(loan);
        log.info("Created loan with id " + loan.getId() + " for user id " + loanCartDto.getUserId());
        return new ResponseEntity<>(loanDto, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/loans/return/{bookId}")
    public ResponseEntity<LoanDto> returnBook(@PathVariable Long bookId) {
        Loan loan = loanService.returnLoanedBook(bookId);

        if (loan == null) {
            throw new ResourceNotFoundException("Book not returned because it was not found! Book id: " + bookId);
        }

        LoanDto loanDto = new LoanDto();
        loanDto = loanDto.createLoanDto(loan);
        log.info("Book with id " + bookId + " returned");
        return new ResponseEntity<>(loanDto, HttpStatus.OK);
    }

    @GetMapping(path = "loans")
    public List<LoanDto> getLoans(@RequestParam Long userId) {
        List<Loan> loans = loanService.getLoans(userId);
        List<LoanDto> loanDtos = new ArrayList<>();

        for (Loan loan : loans) {
            LoanDto loanDto = new LoanDto();
            loanDto = loanDto.createLoanDto(loan);
            loanDtos.add(loanDto);
        }
        return loanDtos;
    }

    @GetMapping(path = "loans/{loanId}")
    public LoanDto getLoan(@PathVariable Long loanId, @RequestParam Long userId) {
        Loan loan = loanService.getLoanByUserIdAndLoanId(userId, loanId);

        if (loan == null) {
            throw new ResourceNotFoundException("Loan with id " + loanId + " not found!");
        }

        LoanDto loanDto = new LoanDto();
        return loanDto.createLoanDto(loan);
    }
}
