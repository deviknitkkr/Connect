package com.vikas.connect.security.oauth2.user;

import lombok.SneakyThrows;

import java.util.Map;

public class OAuth2UserInfoFactory {

    @SneakyThrows
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase("github")) {
            return new GithubOAuth2UserInfo(attributes, registrationId);
        } else {
            return new StdOAuth2UserInfo(attributes, registrationId);
        }
    }
}
