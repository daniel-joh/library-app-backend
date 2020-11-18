package se.djoh.libraryappbackend.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import se.djoh.libraryappbackend.domain.User;
import se.djoh.libraryappbackend.repository.UserRepository;
import se.djoh.libraryappbackend.rest.dto.UpdateUserDto;
import se.djoh.libraryappbackend.rest.dto.UserDto;
import se.djoh.libraryappbackend.service.UserService;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserServiceRealDbTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUserByIdSuccessTest() {
        Long userId = 1L;
        User user = userService.getUserById(userId);
        assertNotNull(user);
    }

    @Test
    public void getUserByIdFailTest() {
        User user = userService.getUserById(null);
        assertNull(user);
    }

    @Test
    public void getUserByUsernameSuccessTest() {
        User user = userService.getUserByUsername("user");
        assertNotNull(user);
    }

    @Test
    public void getUserByUsernameFailTest() {
        User user = userService.getUserByUsername(null);
        assertNull(user);
    }

    @Test
    public void saveUserSuccessTest() {
        long countBeforeSave = userRepository.count();
        User user = new User();

        userService.saveUser(user);

        long countAfterSave = userRepository.count();


        assertEquals(countBeforeSave + 1, countAfterSave);
    }

    @Test
    public void saveUserFailTest() {
        long countBeforeSave = userRepository.count();
        userService.saveUser(null);

        long countAfterSave = userRepository.count();

        assertEquals(countBeforeSave, countAfterSave);
    }

    @Test
    public void deleteUserSuccessTest() {
        long countBeforeDelete = userRepository.count();

        userService.deleteUser(2L);

        long countAfterDelete = userRepository.count();

        assertEquals(countBeforeDelete - 1, countAfterDelete);
    }

    @Test
    public void deleteUserFailTest() {
        long countBeforeDelete = userRepository.count();

        userService.deleteUser(null);

        long countAfterDelete = userRepository.count();

        assertEquals(countBeforeDelete, countAfterDelete);
    }

    @Test
    public void createUserSuccessTest() {
        long countBeforeCreation = userRepository.count();
        UserDto userDto = new UserDto();
        userDto.setUsername("user3");
        userDto.setPassword("user3");

        User user = userService.createUser(userDto);

        long countAfterCreation = userRepository.count();

        assertEquals(countBeforeCreation + 1, countAfterCreation);
        assertNotNull(user);
        assertNotNull(user.getId());
    }

    @Test
    public void createUserFailTest() {
        long countBeforeCreation = userRepository.count();

        User user = userService.createUser(null);

        long countAfterCreation = userRepository.count();

        assertEquals(countBeforeCreation, countAfterCreation);
        assertNull(user);
    }

    @Test
    public void updateUserSuccessTest() {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setUsername("user4");

        User user = new User();
        user.setUsername("user3");

        user = userService.updateUser(user, updateUserDto);

        assertNotNull(user);
        assertEquals(updateUserDto.getUsername(), user.getUsername());
    }

    @Test
    public void updateUserFailTest() {
        User user = userService.updateUser(null, null);

        assertNull(user);
    }
}
