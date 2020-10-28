package se.djoh.libraryappbackend.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserType type;

    private String password;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    private String email;
    private String ssn;

    @Embedded
    private Address address;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private List<Loan> loans = new ArrayList<>();

    public void addLoan(Loan loan) {
        loans.add(loan);
        loan.setUser(this);
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
        loan.setUser(null);
    }

}
