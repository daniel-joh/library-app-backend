
package se.djoh.libraryappbackend.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import se.djoh.libraryappbackend.domain.User;
import se.djoh.libraryappbackend.repository.UserRepository;
import se.djoh.libraryappbackend.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Test
    public void getUserByIdTest() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User retUser = userService.getUserById(userId);
        assertEquals(1, retUser.getId());
    }

    @Test
    public void getUserByIdFailTest() {
        User retUser = userService.getUserById(0L);
        assertNull(retUser);
    }

}

