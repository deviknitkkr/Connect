package com.vikas.connect.security.oauth2;

import com.vikas.connect.security.oauth2.user.OAuth2UserInfo;
import com.vikas.connect.security.oauth2.user.OAuth2UserInfoFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    ConcurrentMap<String, OAuth2UserInfo> map = new ConcurrentHashMap<>();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        System.out.println(oAuth2User.getAttributes());
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        map.put(oAuth2UserInfo.getId(), oAuth2UserInfo);
        return oAuth2User;
    }

    public OAuth2UserInfo getUserInfo(String id) {
        return map.remove(id);
    }
}
