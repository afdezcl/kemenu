package com.kemenu.kemenu_backend.application.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final String appSecret;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, String appSecret) {
        super(authenticationManager);
        this.appSecret = appSecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");

        if (Objects.isNull(authorizationHeader)
                || authorizationHeader.isEmpty()
                || !authorizationHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(authorizationHeader);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String authorizationHeader) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(appSecret.getBytes()))
                .build()
                .verify(authorizationHeader.replace("Bearer ", ""));
        String user = decodedJWT.getSubject();

        if (Objects.nonNull(user) && !user.isEmpty()) {
            List<SimpleGrantedAuthority> authorities = decodedJWT.getClaim("role").asList(String.class).stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(toList());
            return new UsernamePasswordAuthenticationToken(user, null, authorities);
        }

        return null;
    }
}
