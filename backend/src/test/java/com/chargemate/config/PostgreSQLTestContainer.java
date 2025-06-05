package com.chargemate.config;

import org.testcontainers.containers.PostgreSQLContainer;


public class PostgreSQLTestContainer {

    private static final PostgreSQLContainer<?> container;

    static {
        container = new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("chargemate")
                .withUsername("postgres")
                .withPassword("postgres");
        container.start();
    }

    public static PostgreSQLContainer<?> getInstance() {
        return container;
    }
}