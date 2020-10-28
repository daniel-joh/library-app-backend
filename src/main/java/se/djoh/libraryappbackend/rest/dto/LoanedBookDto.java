package se.djoh.libraryappbackend.rest.dto;

import lombok.Data;
import se.djoh.libraryappbackend.domain.Author;
import se.djoh.libraryappbackend.domain.Book;
import se.djoh.libraryappbackend.domain.BookDescription;
import se.djoh.libraryappbackend.domain.LoanItem;

@Data
public class LoanedBookDto {
    //From Book
    private Long bookId;
    private String isbn;

    //From BookDescription
    private String title;

    //From Author
    private String authorName;

    //From LoanItem
    private String loanDate;
    private String dueDate;
    private String returnedDate;

    public LoanedBookDto createLoanedBookDto(LoanItem loanItem) {
        LoanedBookDto loanedBookDto = new LoanedBookDto();

        Book book = loanItem.getBook();
        if (book != null) {
            loanedBookDto.setBookId(book.getId());

            BookDescription bookDescription = book.getBookDescription();
            if (bookDescription != null) {
                loanedBookDto.setIsbn(bookDescription.getIsbn());
                loanedBookDto.setTitle(bookDescription.getTitle());

                Author author = bookDescription.getAuthor();
                loanedBookDto.setAuthorName(author.getFirstName() + " " + author.getLastName());
            }
        }
        if (loanItem.getLoanDate() != null) {
            loanedBookDto.setLoanDate(loanItem.getLoanDate().toString());
        }
        if (loanItem.getDueDate() != null) {
            loanedBookDto.setDueDate(loanItem.getDueDate().toString());
        }
        if (loanItem.getReturnedDate() != null) {
            loanedBookDto.setReturnedDate(loanItem.getReturnedDate().toString());
        }

        return loanedBookDto;
    }
}
