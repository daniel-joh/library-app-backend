
package se.djoh.libraryappbackend.loader;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.djoh.libraryappbackend.domain.User;
import se.djoh.libraryappbackend.domain.UserRole;
import se.djoh.libraryappbackend.domain.UserRoleEnum;
import se.djoh.libraryappbackend.repository.UserRepository;
import se.djoh.libraryappbackend.repository.UserRoleRepository;

import java.util.HashSet;
import java.util.Set;

@Component
@Log
public class UserDataLoader implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void loadUserData() {
        UserRole userRoleUser = userRoleRepository.findByRole(UserRoleEnum.ROLE_USER);
        UserRole userRoleAdmin = userRoleRepository.findByRole(UserRoleEnum.ROLE_ADMIN);

        Set<UserRole> adminUserRoles = new HashSet<>();
        adminUserRoles.add(userRoleUser);
        adminUserRoles.add(userRoleAdmin);

        Set<UserRole> userUserRoles = new HashSet<>();
        userUserRoles.add(userRoleUser);

        User adminUser = userRepository.findByUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("admin"));
        adminUser.setRoles(adminUserRoles);
        adminUser = userRepository.save(adminUser);

        User user = userRepository.findByUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setRoles(userUserRoles);
        user = userRepository.save(user);

        log.info("Users Loaded: " + userRepository.count());
    }

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
}

