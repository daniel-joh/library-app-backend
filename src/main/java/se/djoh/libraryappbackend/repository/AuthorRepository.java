package se.djoh.libraryappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.djoh.libraryappbackend.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
