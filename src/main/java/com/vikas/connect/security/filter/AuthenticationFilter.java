package com.vikas.connect.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vikas.connect.security.TokenProvider;
import com.vikas.connect.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return authenticate;
        } catch (Exception e) {
            sendResponse(response, e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return null;
    }

    @SneakyThrows
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws ServletException {

        String token = tokenProvider.generateToken(authResult.getName());
        sendResponse(response, token, HttpStatus.OK);
    }

    private void sendResponse(HttpServletResponse response, String result, HttpStatus httpStatus) throws IOException {
        ApiResponse<String> response1 = ApiResponse.from(result, httpStatus);
        ObjectMapper objectMapper = new ObjectMapper();

        response.resetBuffer();
        response.setHeader("Content-Type", "application/json");
        response.getOutputStream().print(objectMapper.writeValueAsString(response1));
        response.flushBuffer();
    }
}