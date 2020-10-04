package se.djoh.libraryappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.djoh.libraryappbackend.domain.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
