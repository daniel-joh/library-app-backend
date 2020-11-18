package se.djoh.libraryappbackend.service;

import se.djoh.libraryappbackend.domain.User;
import se.djoh.libraryappbackend.rest.dto.UpdateUserDto;
import se.djoh.libraryappbackend.rest.dto.UserDto;


public interface UserService {
    User getUserById(Long id);

    User getUserByUsername(String username);

    User createUser(UserDto userDto);

    User updateUser(User user, UpdateUserDto userDto);

    User saveUser(User user);

    boolean deleteUser(Long id);

}
