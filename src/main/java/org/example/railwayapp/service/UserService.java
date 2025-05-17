package org.example.railwayapp.service;

import org.example.railwayapp.model.users.User;
import org.example.railwayapp.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional("usersTransactionManager")
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional("usersTransactionManager")
    public void registerUser(String username, String rawPassword, String email) throws Exception {
        if (userRepository.existsByUsername(username)) {
            throw new Exception("Username already exists: " + username);
        }
        if (userRepository.existsByEmail(email)) {
            throw new Exception("Email already exists: " + email);
        }
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User newUser = new User(username, encodedPassword, email, "user");
        userRepository.save(newUser);
    }
}
