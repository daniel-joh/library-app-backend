package se.djoh.libraryappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.djoh.libraryappbackend.domain.BookDescription;

public interface BookDescriptionRepository extends JpaRepository<BookDescription, Long> {
}
