package com.vikas.connect.security.oauth2;

import com.vikas.connect.security.oauth2.user.OAuth2UserInfo;
import com.vikas.connect.security.oauth2.user.OAuth2UserInfoFactory;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class CustomOicdUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    final OidcUserService oidcUserService = new OidcUserService();
    private ConcurrentMap<String, OAuth2UserInfo> map = new ConcurrentHashMap<>();

    @Override
    public OidcUser loadUser(OidcUserRequest oidcUserRequest) throws OAuth2AuthenticationException {

        OidcUser oidcUser = oidcUserService.loadUser(oidcUserRequest);
        OAuth2AccessToken accessToken = oidcUserRequest.getAccessToken();
        oidcUser = new DefaultOidcUser(null, oidcUser.getIdToken(), oidcUser.getUserInfo());
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oidcUserRequest.getClientRegistration().getRegistrationId(), oidcUser.getAttributes());
        map.put(oAuth2UserInfo.getId(), oAuth2UserInfo);
        return oidcUser;
    }

    public OAuth2UserInfo getUserInfo(String id) {
        return map.remove(id);
    }
}
