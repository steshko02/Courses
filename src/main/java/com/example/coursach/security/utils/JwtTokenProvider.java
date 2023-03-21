package com.example.coursach.security.utils;

import com.example.coursach.config.properties.JwtProperties;
import com.example.coursach.security.model.AuthorizedUser;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    private final JwtProperties jwtProperties;

    public JwtTokenProvider(UserDetailsService userDetailsService, JwtProperties jwtProperties) {
        this.userDetailsService = userDetailsService;
        this.jwtProperties = jwtProperties;
    }

    public String createToken(AuthorizedUser userDetails) {
        Date expiration = Date.from(
                LocalDateTime.now()
                        .plusHours(Long.parseLong(jwtProperties.getHours()))
                        .atZone(ZoneId.systemDefault())
                        .toInstant());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                .compact();
    }

    public String createTokenWithBearerPrefix(AuthorizedUser userDetails) {
        return jwtProperties.getPrefix() + createToken(userDetails);
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    private String getEmail(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    public Optional<Authentication> buildAuthentication(HttpServletRequest request) {
        String tokenPrefix = jwtProperties.getPrefix();
        return Optional.of(request).map(r -> r.getHeader(jwtProperties.getAccessTokenKey()))
                .filter(token -> token.startsWith(tokenPrefix))
                .map(token -> token.substring(tokenPrefix.length()))
                .filter(this::validateToken)
                .map(this::getAuthentication);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Token expired or invalid.");
            return false;
        }
    }

}
