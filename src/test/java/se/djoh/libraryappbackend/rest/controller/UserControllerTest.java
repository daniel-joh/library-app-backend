
package se.djoh.libraryappbackend.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import se.djoh.libraryappbackend.domain.Address;
import se.djoh.libraryappbackend.domain.User;
import se.djoh.libraryappbackend.domain.UserRole;
import se.djoh.libraryappbackend.domain.UserRoleEnum;
import se.djoh.libraryappbackend.rest.dto.UpdateUserDto;
import se.djoh.libraryappbackend.rest.dto.UserDto;
import se.djoh.libraryappbackend.service.UserService;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class UserControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private User user = new User();
    private UserDto userDto = new UserDto();
    private UpdateUserDto updateUserDto = new UpdateUserDto();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void loginSuccessfulTest() throws Exception {
        setupUserDto();
        setupUser();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        when(userService.getUserByUsername(userDto.getUsername())).thenReturn(user);

        String postValue = OBJECT_MAPPER.writeValueAsString(userDto);

        MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postValue))
                .andExpect(status().isOk())
                .andReturn();

        UserDto retDto = OBJECT_MAPPER.readValue(storyResult.getResponse().getContentAsString(), UserDto.class);

        assertNotNull(retDto);
    }

    @Test
    public void loginFailTest() throws Exception {
        setupUserDto();
        setupUser();
        user.setPassword(passwordEncoder.encode("wrong_password"));

        when(userService.getUserByUsername(userDto.getUsername())).thenReturn(user);

        String postValue = OBJECT_MAPPER.writeValueAsString(userDto);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postValue))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void registerUserSuccessfulTest() throws Exception {
        setupUserDto();
        setupUser();

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        when(userService.createUser(userDto)).thenReturn(user);

        String postValue = OBJECT_MAPPER.writeValueAsString(userDto);

        MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postValue))
                .andExpect(status().isCreated())
                .andReturn();

        UserDto retDto = OBJECT_MAPPER.readValue(storyResult.getResponse().getContentAsString(), UserDto.class);
        assertNotNull(retDto);
    }

    @Test
    public void registerUserFailTest() throws Exception {
        setupUserDto();

        when(userService.createUser(userDto)).thenReturn(null);

        String postValue = OBJECT_MAPPER.writeValueAsString(userDto);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postValue))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void updateUserWithAuthSuccessfulTest() throws Exception {
        setupUpdateUserDto();
        setupUser();

        when(userService.getUserById(any())).thenReturn(user);
        when(userService.updateUser(any(), any())).thenReturn(user);

        String postValue = OBJECT_MAPPER.writeValueAsString(updateUserDto);

        MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/users")
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(postValue))
                .andExpect(status().isOk())
                .andReturn();

        UpdateUserDto retUpdateUserDto = OBJECT_MAPPER.readValue(storyResult.getResponse().getContentAsString(), UpdateUserDto.class);
        assertNotNull(retUpdateUserDto);
    }

    @Test
    public void updateUserWithAuthWithIncorrectInputShouldResultIn400StatusTest() throws Exception {
        String postValue = OBJECT_MAPPER.writeValueAsString(null);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/users")
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(postValue))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void updateUserWithNoAuthShouldResultIn401StatusTest() throws Exception {
        setupUpdateUserDto();
        setupUser();

        when(userService.getUserById(any())).thenReturn(user);
        when(userService.updateUser(any(), any())).thenReturn(user);

        String postValue = OBJECT_MAPPER.writeValueAsString(updateUserDto);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postValue))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void deleteUserWithAuthSuccessfulTest() throws Exception {
        doReturn(true).when(userService).deleteUser(any());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/users/" + 3L)
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void deleteUserWithAuthWithIncorrectInputShouldResultIn404StatusTest() throws Exception {
        doReturn(false).when(userService).deleteUser(any());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/users/" + 3L)
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void deleteUserWithNoAuthShouldResultIn401StatusTest() throws Exception {
        doReturn(true).when(userService).deleteUser(any());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/users/" + 3L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    private void setupUser() {
        user.setId(3L);
        user.setUsername("user1");
        UserRole role = new UserRole();
        role.setRole(UserRoleEnum.ROLE_USER);
        Set<UserRole> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        user.setEmail("test@mail.se");
        user.setSsn("1989898987656");
        user.setPhoneNumber("08898989");
        user.setFirstName("user");
        user.setLastName("user");

        Address adress = new Address();
        adress.setStreetAddress("gata");
        adress.setCity("stad");
        adress.setZipCode(12345);
        adress.setCountry("sverige");
        user.setAddress(adress);
    }

    private void setupUserDto() {
        userDto.setUsername("user1");
        userDto.setPassword("user");
        userDto.setUserRole("ROLE_USER");
        userDto.setEmail("test@mail.se");
        userDto.setSsn("1989898987656");
        userDto.setPhoneNumber("08989898");
        userDto.setFirstName("user");
        userDto.setLastName("user");
        userDto.setStreetAddress("gata");
        userDto.setCity("stad");
        userDto.setZipCode(12345);
        userDto.setCountry("sverige");
    }

    private void setupUpdateUserDto() {
        updateUserDto.setUsername("user1");
        updateUserDto.setUserRole("ROLE_USER");
        updateUserDto.setEmail("test@mail.se");
        updateUserDto.setSsn("1989898987656");
        updateUserDto.setPhoneNumber("08989898");
        updateUserDto.setFirstName("user");
        updateUserDto.setLastName("user");
        updateUserDto.setStreetAddress("gata");
        updateUserDto.setCity("stad");
        updateUserDto.setZipCode(12345);
        updateUserDto.setCountry("sverige");
    }


}

