package com.vikas.connect.security.oauth2.user;

import java.util.Map;

public class OneLoginOAuth2UserInfo extends OAuth2UserInfo{

    public OneLoginOAuth2UserInfo(Map<String, Object> attributes) {
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
        super.setImageUrl("no picture");
        return super.getImageUrl();
    }
}
