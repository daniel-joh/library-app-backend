package se.djoh.libraryappbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="loans")
@Data
public class Loan {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="loan", cascade = CascadeType.ALL)
    private List<LoanItem> loanItems;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;

    private boolean active;

    public void inactivate() {

    }

}
