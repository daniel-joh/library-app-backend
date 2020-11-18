package se.djoh.libraryappbackend.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="book_descriptions")
@Data
public class BookDescription {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String isbn;
    private String title;
    private String summary;
    private String language;

    @Column(name="published_year")
    private Integer publishedYear;

    @OneToOne()
    @JoinColumn(name="author_id")
    private Author author;

    @OneToOne()
    @JoinColumn(name="genre_id")
    private Genre genre;

    @Column(name="number_of_pages")
    private int numberOfPages;

    @Column(name="image_url")
    private String imageUrl;

    @ToString.Exclude
    @OneToMany(mappedBy="bookDescription", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<Book> books;

    @Enumerated(EnumType.STRING)
    private BookFormatEnum format;

    @ToString.Exclude
    @ManyToOne()
    @JoinColumn(name="publisher_id")
    private Publisher publisher;

    public void addBook(Book book) {
        books.add(book);
        book.setBookDescription(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setBookDescription(null);
    }

}
