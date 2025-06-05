package com.chargemate.openchargemap.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "openchargemap.api")
@Getter
@Setter
public class OpenChargeMapProperties {
    private String key;
    private String baseUrl;
}
