package com.green.greengram4.common;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter                        //prefix 접두사, 앞에 app붙임
@ConfigurationProperties(prefix = "app")  //기본 클래스 Greengram4Application에 ConfigurationPropertiesScan빈 등록
public class AppProperties {

    private final Jwt jwt = new Jwt();

    @Getter
    @Setter
    public class Jwt { //이너 클래스
        private String secret;
        private String headerSchemeName;
        private String tokenType;
        private long accessTokenExPiry;
        private long refreshTokenExPiry;
    }
}
