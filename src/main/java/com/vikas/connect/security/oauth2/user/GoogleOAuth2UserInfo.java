package com.vikas.connect.security.oauth2.user;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        super.setId((String) attributes.get("sub"));
        return super.getId();
    }

    @Override
    public String getName() {
        super.setName((String) attributes.get("name"));
        return super.getName();
    }

    @Override
    public String getEmail() {
        super.setEmail((String) attributes.get("email"));
        return super.getEmail();
    }

    @Override
    public String getImageUrl() {
        super.setImageUrl((String) attributes.get("picture"));
        return super.getImageUrl();
    }
}
