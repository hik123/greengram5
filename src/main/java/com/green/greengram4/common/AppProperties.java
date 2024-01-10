package com.green.greengram4.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Jwt jwt = new Jwt();

    @Getter
    @Setter
    public class Jwt {
        private String secret;
        private String headerSchemeName;
        private String tokenType;
        private long accessTokenExpiry;
        private long refreshTokenExpiry;
    }
}
