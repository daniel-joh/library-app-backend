package se.djoh.libraryappbackend.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.MERGE, fetch=FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> roles = new HashSet<>();

    private String username;

    private String password;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    private String email;
    private String ssn;
    private String phoneNumber;

    @Embedded
    private Address address;

    @ToString.Exclude
    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private List<Loan> loans = new ArrayList<>();

    //needed for spring security
    @Column(name="account_non_expired")
    private Boolean accountNonExpired = true;
    @Column(name="account_non_locked")
    private Boolean accountNonLocked = true;
    @Column(name="credentials_non_expired")
    private Boolean credentialsNonExpired = true;
    @Column(name="enabled")
    private Boolean enabled = true;

    public void addLoan(Loan loan) {
        loans.add(loan);
        loan.setUser(this);
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
        loan.setUser(null);
    }

}
