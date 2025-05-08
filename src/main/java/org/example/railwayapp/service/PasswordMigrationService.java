package org.example.railwayapp.service;

import jakarta.annotation.PostConstruct;
import org.example.railwayapp.model.users.User;
import org.example.railwayapp.repository.users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PasswordMigrationService {

    private static final Logger logger = LoggerFactory.getLogger(PasswordMigrationService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostConstruct
    @Transactional("usersTransactionManager")
    public void migratePasswords() {
        logger.info("Starting password migration...");

        // Находим всех пользователей
        List<User> users = userRepository.findAll();

        int migratedCount = 0;
        for (User user : users) {
            String currentPassword = user.getPassword();

            // Проверяем, нужно ли шифровать пароль
            if (currentPassword != null &&
                    !currentPassword.startsWith("$2a$") &&
                    !currentPassword.startsWith("$2b$") &&
                    !currentPassword.startsWith("$2y$")) {

                try {
                    // Шифруем открытый пароль
                    String hashedPassword = passwordEncoder.encode(currentPassword);
                    // Устанавливаем зашифрованный пароль
                    user.setPassword(hashedPassword);
                    // Сохраняем пользователя обратно в базу
                    userRepository.save(user);
                    migratedCount++;
                    logger.info("Hashed password for user: {}", user.getUsername());
                } catch (Exception e) {
                    logger.error("Failed to hash password for user: " + user.getUsername(), e);
                }
            }
        }

        if (migratedCount > 0) {
            logger.info("Password migration finished. Hashed {} user passwords.", migratedCount);
        } else {
            logger.info("Password migration finished. No passwords needed hashing.");
        }
    }
}