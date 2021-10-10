package com.vikas.connect.security.oauth2;

import com.vikas.connect.security.TokenProvider;
import com.vikas.connect.security.oauth2.user.OAuth2UserInfo;
import com.vikas.connect.util.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2UserInfo oAuth2UserInfo = customOAuth2UserService.getUserInfo(authentication.getName());
        oAuth2UserInfo.setToken(tokenProvider.generateToken(oAuth2UserInfo.getId()));
        ApiResponse.sendResponse(response, oAuth2UserInfo, HttpStatus.OK);
    }

}
