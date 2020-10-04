package se.djoh.libraryappbackend.domain;

import lombok.Data;

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

    @OneToOne()
    @JoinColumn(name="author_id")
    private Author author;

    @OneToOne()
    @JoinColumn(name="genre_id")
    private Genre genre;

    @Column(name="number_of_pages")
    private int numberOfPages;

    private byte[] image;

    @OneToMany(mappedBy="bookDescription", cascade = CascadeType.ALL)
    private List<Book> books;
}
