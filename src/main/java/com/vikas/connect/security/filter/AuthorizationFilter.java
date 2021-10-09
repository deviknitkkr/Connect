package com.vikas.connect.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.vikas.connect.security.TokenProvider;
import com.vikas.connect.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private static final String[] excluded_urls = {
            "/login",
            "/register",
            "/favicon.ico"
    };

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private TokenProvider tokenProvider;
    @Value("${app.jwt-header}")
    private String jwtHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(jwtHeader);

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring("Bearer ".length());

            String result = tokenProvider.validateToken(token);

            if (!result.equals("valid")) {
                sendResponse(response, result);
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(tokenProvider.getUsernameFromJWT(token), null, null));
            filterChain.doFilter(request, response);

        } else
            sendResponse(response, jwtHeader + " token not provided");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String url = request.getRequestURI();
        return Stream.of(excluded_urls).anyMatch(x -> pathMatcher.match(x, url));
    }

    void sendResponse(HttpServletResponse response, String result) throws IOException {
        ApiResponse<String> error = ApiResponse.from("", result, HttpStatus.UNAUTHORIZED);
        ObjectMapper objectMapper = new ObjectMapper();

        response.resetBuffer();
        response.setHeader("Content-Type", "application/json");
        response.getOutputStream().print(objectMapper.writeValueAsString(error));
        response.flushBuffer();
    }
}
