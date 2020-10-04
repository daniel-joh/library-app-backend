package se.djoh.libraryappbackend.domain;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="loan_items")
@Data
public class LoanItem {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name="loan_id")
    private Loan loan;

    @OneToOne()
    @JoinColumn(name="book_id")
    private Book book;

    @Column(name="loan_date")
    private LocalDate loanDate;

    @Column(name="due_date")
    private LocalDate dueDate;

    @Column(name="returned_date")
    private LocalDate returnedDate;
}
