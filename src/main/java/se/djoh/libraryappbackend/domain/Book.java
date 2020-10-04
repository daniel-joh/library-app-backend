package se.djoh.libraryappbackend.domain;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name="books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "book")
    private LoanItem loanItem;

    @Column(name="available_for_loan")
    private boolean availableForLoan;

    private String shelf;

    @ManyToOne()
    @JoinColumn(name="book_description_id")
    private BookDescription bookDescription;

}
