package se.djoh.libraryappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.djoh.libraryappbackend.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailIgnoreCase(String email);

    User findBySsn(String ssn);
}
