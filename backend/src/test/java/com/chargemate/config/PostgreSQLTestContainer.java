package com.chargemate.config;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import java.time.Duration;

public class PostgreSQLTestContainer {

    private static final PostgreSQLContainer<?> container;

    static {
        container = new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("chargemate")
                .withUsername("postgres")
                .withPassword("postgres")
                .withReuse(true)
                .waitingFor(Wait.forListeningPort())
                .withStartupTimeout(Duration.ofSeconds(60));
        container.start();
    }

    public static PostgreSQLContainer<?> getInstance() {
        return container;
    }
}