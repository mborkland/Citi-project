package edu.usf.cse.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenAuthenticationService {

    private static final String AUTH_TOKEN_HEADER = "X-Auth-Token";

    private final TokenHandler tokenHandler;

    public TokenAuthenticationService(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    public void addTokenToResponse(UserAuthentication authentication, HttpServletResponse response) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        String token = tokenHandler.createToken(userDetails);
        response.addHeader(AUTH_TOKEN_HEADER, token);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_TOKEN_HEADER);
        if (token != null) {
            final UserDetails userDetails = tokenHandler.getUser(token);
            if (userDetails != null) {
                UserAuthentication userAuthentication = new UserAuthentication(userDetails);
                userAuthentication.setAuthenticated(true);
                return userAuthentication;
            }
        }

        return null;
    }
}
