package com.epam.esm.model.dao;

import com.epam.esm.model.domain.GiftCertificate;
import com.epam.esm.model.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.epam.esm.model.domain.EntityBuilder.generateCertificates;
import static com.epam.esm.model.domain.EntityBuilder.generateUsers;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        List<User> users = generateUsers(10);
        assertFalse(users.isEmpty());

        List<GiftCertificate> giftCertificates = generateCertificates(10);
        assertFalse(giftCertificates.isEmpty());

//        List<User> save = userRepository.saveAll(users);
    }
}
