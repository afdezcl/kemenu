package com.kemenu.kemenu_backend.application.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper mapper;
    private final Recaptcha recaptcha;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            JsonNode requestJson = mapper.readTree(request.getInputStream());
            String username = requestJson.get("email").asText();
            String password = requestJson.get("password").asText();
            String recaptchaToken = requestJson.get("recaptchaToken").asText();

            if (recaptcha.isValid(recaptchaToken)) {
                return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            } else {
                throw new InsufficientAuthenticationException("Incorrect captcha");
            }
        } catch (IOException e) {
            throw new InsufficientAuthenticationException("Error while authentication", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) {
        User user = (User) auth.getPrincipal();
        String[] roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);

        String accessToken = jwtService.generateAccessToken(user.getUsername(), roles);
        String refreshToken = jwtService.generateRefreshToken(user.getUsername(), roles);

        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addHeader("JWT-Refresh-Token", "Bearer " + refreshToken);
    }
}
