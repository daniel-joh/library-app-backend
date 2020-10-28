package se.djoh.libraryappbackend.service.impl;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.djoh.libraryappbackend.domain.User;
import se.djoh.libraryappbackend.repository.UserRepository;
import se.djoh.libraryappbackend.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@Log
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        User retUser = null;

        if (user.isPresent()) {
            retUser = user.get();
        }
        return retUser;
    }

}
