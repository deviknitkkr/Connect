package com.vikas.connect.security.oauth2.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Data
public abstract class OAuth2UserInfo {

    @JsonIgnore
    final protected Map<String, Object> attributes;
    private String id;
    private String name;
    private String email;
    private String imageUrl;
    private String token;
}
