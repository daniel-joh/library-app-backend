package se.djoh.libraryappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.djoh.libraryappbackend.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
