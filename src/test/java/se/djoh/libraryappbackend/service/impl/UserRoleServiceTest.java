package se.djoh.libraryappbackend.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import se.djoh.libraryappbackend.domain.UserRole;
import se.djoh.libraryappbackend.domain.UserRoleEnum;
import se.djoh.libraryappbackend.repository.UserRoleRepository;
import se.djoh.libraryappbackend.service.UserRoleService;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserRoleServiceTest {
    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserRoleService userRoleService = new UserRoleServiceImpl();

    @Test
    public void gettingRoleSuccessfulTest() {
        UserRole role = new UserRole();
        when(userRoleRepository.findByRole(any())).thenReturn(role);

        UserRole retRole = userRoleService.getByRole(UserRoleEnum.ROLE_USER);

        assertNotNull(retRole);
    }

    @Test
    public void gettingRoleWithNoFoundRoleTest() {
        when(userRoleRepository.findByRole(any())).thenReturn(null);

        UserRole retRole = userRoleService.getByRole(UserRoleEnum.ROLE_USER);

        assertNull(retRole);
    }


}
