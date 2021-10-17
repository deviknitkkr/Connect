package com.vikas.connect.security.oauth2.user;

import com.vikas.connect.util.AuthProvider;
import lombok.SneakyThrows;

import java.util.Map;

public class OAuth2UserInfoFactory {

    @SneakyThrows
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
            return new FacebookOAuth2UserInfo(attributes,registrationId);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.github.toString())) {
            return new GithubOAuth2UserInfo(attributes,registrationId);
        } else {
            return new StdOAuth2UserInfo(attributes,registrationId);
        }
    }
}
