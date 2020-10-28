package se.djoh.libraryappbackend.rest.dto;

import lombok.Data;
import se.djoh.libraryappbackend.domain.Book;
import se.djoh.libraryappbackend.domain.BookDescription;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookDto {
    //From Book
    private long bookId;
    private boolean availableForLoan;
    private String shelf;

    //From BookDescription
    private String isbn;
    private String title;
    private String summary;
    private int numberOfPages;
    private String imageUrl;

    //From Author
    private String authorName;

    //From Genre
    private String genreName;

    //Creates List of BookDto:s from BookDescription and Book
    public static List<BookDto> createBookDtoList(BookDescription bookDesc) {
        List<BookDto> bookDtos = new ArrayList<>();

        List<Book> books = bookDesc.getBooks();
        for (Book book : books) {
            BookDto dto = new BookDto();
            dto.setBookId(book.getId());
            dto.setAvailableForLoan(book.isAvailableForLoan());
            dto.setShelf(book.getShelf());
            bookDtos.add(dto);
        }

        for (BookDto dto : bookDtos) {
            dto.setIsbn(bookDesc.getIsbn());
            dto.setTitle(bookDesc.getTitle());
            dto.setSummary(bookDesc.getSummary());
            dto.setNumberOfPages(bookDesc.getNumberOfPages());
            dto.setImageUrl(bookDesc.getImageUrl());

            if (bookDesc.getAuthor() != null) {
                dto.setAuthorName(bookDesc.getAuthor().getFirstName() + " " + bookDesc.getAuthor().getLastName());
            }

            if (bookDesc.getGenre() != null) {
                dto.setGenreName(bookDesc.getGenre().getName());
            }
        }

        return bookDtos;
    }

    //Creates BookDto from BookDescription
    public static BookDto createBookDto(BookDescription bookDesc) {
        BookDto dto = new BookDto();
        dto.setIsbn(bookDesc.getIsbn());
        dto.setTitle(bookDesc.getTitle());
        dto.setSummary(bookDesc.getSummary());
        dto.setNumberOfPages(bookDesc.getNumberOfPages());
        dto.setImageUrl(bookDesc.getImageUrl());

        if (bookDesc.getAuthor() != null) {
            dto.setAuthorName(bookDesc.getAuthor().getFirstName() + " " + bookDesc.getAuthor().getLastName());
        }

        if (bookDesc.getGenre() != null) {
            dto.setGenreName(bookDesc.getGenre().getName());
        }
        return dto;
    }


}
