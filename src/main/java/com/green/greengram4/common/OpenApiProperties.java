package com.green.greengram4.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "openapi")
public class OpenApiProperties {
    private final Apartment apartment = new Apartment();

    @Getter
    @Setter
    public static class Apartment {
        private String baseUrl;
        private String dataUrl;
        private String serviceKey;
    }
}
