package se.djoh.libraryappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.djoh.libraryappbackend.domain.LoanItem;

public interface LoanItemRepository extends JpaRepository<LoanItem, Long> {
}
