package se.djoh.libraryappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.djoh.libraryappbackend.domain.UserRole;
import se.djoh.libraryappbackend.domain.UserRoleEnum;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    UserRole findByRole(UserRoleEnum role);
}
