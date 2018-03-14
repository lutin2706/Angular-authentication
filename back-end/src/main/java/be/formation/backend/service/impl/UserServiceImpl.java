package be.formation.backend.service.impl;

import be.formation.backend.model.entity.Authority;
import be.formation.backend.model.entity.User;
import be.formation.backend.repository.AuthorityRepository;
import be.formation.backend.repository.UserRepository;
import be.formation.backend.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password, boolean isAdmin) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setAuthorities(new ArrayList<>(Collections.singletonList(createOrGetAuthority("ROLE_USER"))));
        if (isAdmin) {
            user.getAuthorities().add(createOrGetAuthority("ROLE_ADMIN"));
        }
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public Authority createOrGetAuthority(String authority) {

        Authority found = authorityRepository.findByAuthority(authority);

        if (found == null) {
            found = new Authority(authority);
            authorityRepository.save(found);
        }

        return found;
    }
}
