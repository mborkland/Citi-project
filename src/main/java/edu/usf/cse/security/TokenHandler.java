package edu.usf.cse.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class TokenHandler {

    private final String secretToken;

    private final UserDetailsService userDetailsService;

    public TokenHandler(String secretToken, UserDetailsService userDetailsService) {
        this.secretToken = secretToken;
        this.userDetailsService = userDetailsService;
    }

    public UserDetails getUser(String token) {
        String username = Jwts.parser().setSigningKey(secretToken).parseClaimsJws(token).getBody().getSubject();
        return userDetailsService.loadUserByUsername(username);
    }

    public String createToken(UserDetails user) {
        return Jwts.builder().setSubject(user.getUsername()).signWith(SignatureAlgorithm.HS512, secretToken).compact();
    }
}
