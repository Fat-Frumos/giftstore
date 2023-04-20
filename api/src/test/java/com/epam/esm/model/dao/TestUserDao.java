package com.epam.esm.model.dao;

import com.epam.esm.api.RestApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.epam.esm.model.entity.EntityBuilder.USERNAMES;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
@SpringBootTest(classes = RestApiApplication.class)
class TestUserDao {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveNames() {
        System.err.println(USERNAMES.size() + " names");
        for (int i = 0; i < 5; i++) {
//            String randomName = getRandomElement(USERNAMES);
//            User user = User.builder().username(randomName).email(randomName + "@i.ua").build();
//            assertNotNull(user.getUsername());
//            User save = userRepository.save(user);
//            System.out.println(save);
        }
    }

//    @Test
//    void getNames() {
//        Iterable<User> all = userRepository.findAll();
//        System.out.println(all);
//        assertNotNull(all);
//    }
}
