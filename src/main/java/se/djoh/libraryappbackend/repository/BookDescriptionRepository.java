package se.djoh.libraryappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.djoh.libraryappbackend.domain.BookDescription;

import java.util.List;

public interface BookDescriptionRepository extends JpaRepository<BookDescription, Long> {
}
