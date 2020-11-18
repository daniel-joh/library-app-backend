package se.djoh.libraryappbackend.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="loans")
@Data
public class Loan {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @OneToMany(mappedBy="loan", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LoanItem> loanItems = new ArrayList<>();

    @ToString.Exclude
    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;

    private boolean active;

    @Column(name="created_date")
    private LocalDate createdDate;

    public void addLoanItem(LoanItem loanitem) {
        loanItems.add(loanitem);
        loanitem.setLoan(this);
    }

    public void removeLoanItem(LoanItem loanitem) {
        loanItems.remove(loanitem);
        loanitem.setLoan(null);
    }
}
