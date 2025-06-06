package com.chargemate.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SwaggerConfigUnitTest {

    private final SwaggerConfig swaggerConfig = new SwaggerConfig();

    @Test
    void shouldCreateOpenAPIWithCorrectInfo() {
        // Act
        OpenAPI openAPI = swaggerConfig.customOpenAPI();

        // Assert
        assertThat(openAPI).isNotNull();
        
        Info info = openAPI.getInfo();
        assertThat(info).isNotNull();
        assertThat(info.getTitle()).isEqualTo("ChargeMate API");
        assertThat(info.getVersion()).isEqualTo("1.0");
        assertThat(info.getDescription()).isEqualTo("API for locating and managing EV charging stations.");
    }

    @Test
    void shouldCreateOpenAPIWithSecurityScheme() {
        // Act
        OpenAPI openAPI = swaggerConfig.customOpenAPI();

        // Assert
        assertThat(openAPI).isNotNull();
        
        Components components = openAPI.getComponents();
        assertThat(components).isNotNull();
        
        var securitySchemes = components.getSecuritySchemes();
        assertThat(securitySchemes).isNotNull();
        assertThat(securitySchemes).containsKey("bearerAuth");
        
        SecurityScheme securityScheme = securitySchemes.get("bearerAuth");
        assertThat(securityScheme).isNotNull();
        assertThat(securityScheme.getType()).isEqualTo(SecurityScheme.Type.HTTP);
        assertThat(securityScheme.getScheme()).isEqualTo("bearer");
        assertThat(securityScheme.getBearerFormat()).isEqualTo("JWT");
    }
} 