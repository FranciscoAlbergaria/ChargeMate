package com.chargemate.repository;

import com.chargemate.model.User;
import com.chargemate.model.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import app.getxray.xray.junit.customjunitxml.annotations.Requirement;
import app.getxray.xray.junit.customjunitxml.annotations.XrayTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    @XrayTest(key = "CMATE-83")
    @Requirement("CMATE-63") 
    void saveEVDriver_ShouldPersistUser() {
        User evDriver = new User();
        evDriver.setEmail("driver@example.com");
        evDriver.setPassword("encodedPassword");
        evDriver.setName("John Driver");
        evDriver.setUserType(UserType.EV_DRIVER);

        User savedUser = userRepository.save(evDriver);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(evDriver.getEmail());
        assertThat(savedUser.getUserType()).isEqualTo(UserType.EV_DRIVER);
    }

    @Test
    @XrayTest(key = "CMATE-83")
    @Requirement("CMATE-63")
    void findByEmail_ShouldReturnUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setName("Test User");
        user.setUserType(UserType.EV_DRIVER);

        entityManager.persist(user);
        entityManager.flush();

        User found = userRepository.findByEmail(user.getEmail()).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @XrayTest(key = "CMATE-83")
    @Requirement("CMATE-63") 
    void existsByEmail_ShouldReturnTrue_WhenEmailExists() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setName("Test User");
        user.setUserType(UserType.EV_DRIVER);

        entityManager.persist(user);
        entityManager.flush();

        boolean exists = userRepository.existsByEmail(user.getEmail());

        assertThat(exists).isTrue();
    }

    @Test
    @XrayTest(key = "CMATE-83")
    @Requirement("CMATE-63")
    void existsByEmail_ShouldReturnFalse_WhenEmailDoesNotExist() {
        boolean exists = userRepository.existsByEmail("nonexistent@example.com");

        assertThat(exists).isFalse();
    }
} 