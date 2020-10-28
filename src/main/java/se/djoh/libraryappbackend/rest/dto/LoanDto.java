package se.djoh.libraryappbackend.rest.dto;

import lombok.Data;
import se.djoh.libraryappbackend.domain.Loan;
import se.djoh.libraryappbackend.domain.LoanItem;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoanDto {
    private Long loanId;
    private boolean active;
    private String createdDate;
    private Long userId;
    private List<LoanedBookDto> loanedBooks = new ArrayList<>();

    public LoanDto createLoanDto(Loan loan) {
        LoanDto loanDto = new LoanDto();

        loanDto.setActive(loan.isActive());
        if (loan.getCreatedDate() != null) {
            loanDto.setCreatedDate(loan.getCreatedDate().toString());
        }

        loanDto.setLoanId(loan.getId());
        if (loan.getUser() != null) {
            loanDto.setUserId(loan.getUser().getId());
        }

        for (LoanItem loanItem : loan.getLoanItems()) {
            LoanedBookDto loanedBookDto = new LoanedBookDto();
            loanedBookDto = loanedBookDto.createLoanedBookDto(loanItem);
            loanedBooks.add(loanedBookDto);
        }
        loanDto.setLoanedBooks(loanedBooks);
        return loanDto;
    }

}
