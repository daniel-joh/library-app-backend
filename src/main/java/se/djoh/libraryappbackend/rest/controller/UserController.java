package se.djoh.libraryappbackend.rest.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import se.djoh.libraryappbackend.domain.User;
import se.djoh.libraryappbackend.domain.UserRole;
import se.djoh.libraryappbackend.rest.dto.UpdateUserDto;
import se.djoh.libraryappbackend.rest.dto.UserDto;
import se.djoh.libraryappbackend.rest.exception.ResourceNotCreatedException;
import se.djoh.libraryappbackend.rest.exception.ResourceNotFoundException;
import se.djoh.libraryappbackend.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@Log
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(path = "/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto) {
        User user = userService.getUserByUsername(userDto.getUsername());

        if (user == null) {
            throw new UsernameNotFoundException("User name: " + userDto.getUsername() + " not found");
        }

        String userRole = UserRole.determineHighestUserRole(user);

        if (userDto.getPassword() != null && passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            UserDto retUserDto = new UserDto(user.getId(), user.getUsername(), null, user.getEmail(), user.getSsn(), user.getPhoneNumber(), user.getFirstName(), user.getLastName(),
                    user.getAddress().getStreetAddress(), user.getAddress().getCity(), user.getAddress().getZipCode(), user.getAddress().getCountry(), userRole);
            log.info("User with id " + user.getId() + " logged in");
            return new ResponseEntity<>(retUserDto, HttpStatus.OK);
        } else {
            log.info("User with id " + user.getId() + " not authorized!");
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(path = "/users")
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserDto userDto) {
        User user = userService.createUser(userDto);

        if (user == null) {
            throw new ResourceNotCreatedException("User not created!");
        }

        UserDto retUserDto = new UserDto(user.getId(), user.getUsername(), null, user.getEmail(), user.getSsn(), user.getPhoneNumber(), user.getFirstName(), user.getLastName(),
                user.getAddress().getStreetAddress(), user.getAddress().getCity(), user.getAddress().getZipCode(), user.getAddress().getCountry(), "user");

        return new ResponseEntity<>(retUserDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/users")
    public ResponseEntity<UpdateUserDto> updateUser(@RequestBody @Valid UpdateUserDto updateUserDto) {
        User user = userService.getUserById(updateUserDto.getId());

        if (user == null) {
            throw new UsernameNotFoundException("User with id " + updateUserDto.getId() + " not found");
        }

        user = userService.updateUser(user, updateUserDto);
        log.info("User with id: " + user.getId() + " updated");

        String userRole = UserRole.determineHighestUserRole(user);

        UpdateUserDto retUpdateUserDto = new UpdateUserDto(user.getId(), user.getUsername(), null, user.getEmail(), user.getSsn(),
                user.getPhoneNumber(), user.getFirstName(), user.getLastName(), user.getAddress().getStreetAddress(),
                user.getAddress().getCity(), user.getAddress().getZipCode(), user.getAddress().getCountry(), userRole);

        return new ResponseEntity<>(retUpdateUserDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userService.deleteUser(id)) {
            throw new ResourceNotFoundException("Unable to find user to delete!");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
