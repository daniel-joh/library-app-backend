package se.djoh.libraryappbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private LoanItem loanItem;

    @Column(name="available_for_loan")
    private boolean availableForLoan;

    private String shelf;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="book_description_id")
    @JsonIgnore
    private BookDescription bookDescription;

}
