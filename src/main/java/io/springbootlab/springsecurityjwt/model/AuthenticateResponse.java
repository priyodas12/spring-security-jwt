package io.springbootlab.springsecurityjwt.model;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

public class AuthenticateResponse {

    private final String jwt;

    public AuthenticateResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
