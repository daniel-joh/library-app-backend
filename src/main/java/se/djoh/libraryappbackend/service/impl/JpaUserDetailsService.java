package se.djoh.libraryappbackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.djoh.libraryappbackend.domain.User;
import se.djoh.libraryappbackend.domain.UserRole;
import se.djoh.libraryappbackend.domain.UserRoleEnum;
import se.djoh.libraryappbackend.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Getting User info via JPA");

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User name: " + username + " not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getEnabled(), user.getAccountNonExpired(), user.getCredentialsNonExpired(),
                user.getAccountNonLocked(), convertRolesToSimpleGrantedAuthorities(user.getRoles()));
    }

    private Collection<SimpleGrantedAuthority> convertRolesToSimpleGrantedAuthorities(Set<UserRole> roles) {
        if (!roles.isEmpty()) {
            return roles.stream()
                    .map(UserRole::getRole)
                    .map(UserRoleEnum::name)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());

        } else {
            return new HashSet<>();
        }
    }
}
