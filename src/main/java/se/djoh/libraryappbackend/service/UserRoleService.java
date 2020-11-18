package se.djoh.libraryappbackend.service;

import se.djoh.libraryappbackend.domain.UserRole;
import se.djoh.libraryappbackend.domain.UserRoleEnum;

public interface UserRoleService {
    UserRole getByRole(UserRoleEnum role);
}
