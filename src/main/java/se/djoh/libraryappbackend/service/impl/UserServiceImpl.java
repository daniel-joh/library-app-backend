package se.djoh.libraryappbackend.service.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.djoh.libraryappbackend.domain.Address;
import se.djoh.libraryappbackend.domain.User;
import se.djoh.libraryappbackend.domain.UserRole;
import se.djoh.libraryappbackend.domain.UserRoleEnum;
import se.djoh.libraryappbackend.repository.UserRepository;
import se.djoh.libraryappbackend.rest.dto.UpdateUserDto;
import se.djoh.libraryappbackend.rest.dto.UserDto;
import se.djoh.libraryappbackend.service.UserRoleService;
import se.djoh.libraryappbackend.service.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Log
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public User getUserById(Long id) {
        if (id == null) {
            return null;
        }
        Optional<User> user = userRepository.findById(id);
        User retUser = null;

        if (user.isPresent()) {
            retUser = user.get();
        }
        return retUser;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = null;
        if (username != null) {
            user = userRepository.findByUsername(username);
        }
        return user;
    }

    @Override
    public User saveUser(User user) {
        User retUser = null;
        if (user != null) {
            retUser = userRepository.save(user);
        }
        return retUser;
    }

    @Override
    public boolean deleteUser(Long id) {
        if (id == null) {
            log.info("Unable to delete user with id " + id + " due to call with null id");
            return false;
        }

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("User with id " + id + " deleted");
            return true;
        } else {
            log.info("Unable to delete user with id " + id + " due to it was not found");
            return false;
        }
    }

    @Override
    public User createUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setSsn(userDto.getSsn());
        user.setPhoneNumber(userDto.getPhoneNumber());

        UserRole userRoleUser = userRoleService.getByRole(UserRoleEnum.ROLE_USER);
        Set<UserRole> roles = new HashSet<>();
        roles.add(userRoleUser);
        user.setRoles(roles);

        Address address = new Address();
        address.setStreetAddress(userDto.getStreetAddress());
        address.setCity(userDto.getCity());
        address.setZipCode(userDto.getZipCode());
        address.setCountry(userDto.getCountry());
        user.setAddress(address);

        user = saveUser(user);
        log.info("User created with id: " + user.getId());
        return user;
    }

    @Override
    public User updateUser(User user, UpdateUserDto userDto) {
        if (user == null || userDto == null) {
            return null;
        }

        if (userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }

        if (userDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getSsn() != null) {
            user.setSsn(userDto.getSsn());
        }
        if (userDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }
        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }

        if (user.getAddress() != null) {
            if (userDto.getStreetAddress() != null) {
                user.getAddress().setStreetAddress(userDto.getStreetAddress());
            }
            if (userDto.getCity() != null) {
                user.getAddress().setCity(userDto.getCity());
            }
            if (userDto.getZipCode() != null) {
                user.getAddress().setZipCode(userDto.getZipCode());
            }
            if (userDto.getCountry() != null) {
                user.getAddress().setCountry(userDto.getCountry());
            }
        }
        return saveUser(user);
    }

}
