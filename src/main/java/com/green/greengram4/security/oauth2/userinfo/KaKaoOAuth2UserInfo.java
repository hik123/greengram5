package com.green.greengram4.security.oauth2.userinfo;

import java.util.Map;


public class KaKaoOAuth2UserInfo extends OAuth2UserInfo{
    public KaKaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>)attributes.get("properties");
        return properties == null ? null : (String)properties.get("nickname");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("account_email");
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> properties = (Map<String, Object>)attributes.get("properties");
        return properties == null ? null : (String) properties.get("thumbnail_image");
    }
}
