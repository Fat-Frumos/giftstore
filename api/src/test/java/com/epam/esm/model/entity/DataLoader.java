package com.epam.esm.model.entity;

import com.epam.esm.model.dao.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.epam.esm.model.entity.EntityBuilder.generateCertificates;
import static com.epam.esm.model.entity.EntityBuilder.generateUsers;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        List<User> users = generateUsers(10);
        assertFalse(users.isEmpty());
        List<Certificate> certificates = generateCertificates(100);
        assertFalse(certificates.isEmpty());

//        List<User> save = userRepository.saveAll(users);
    }
}
