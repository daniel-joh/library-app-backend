package se.djoh.libraryappbackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.djoh.libraryappbackend.domain.UserRole;
import se.djoh.libraryappbackend.domain.UserRoleEnum;
import se.djoh.libraryappbackend.repository.UserRoleRepository;
import se.djoh.libraryappbackend.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserRole getByRole(UserRoleEnum role) {
        return userRoleRepository.findByRole(role);
    }
}
