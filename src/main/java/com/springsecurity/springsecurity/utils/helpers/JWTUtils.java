package com.springsecurity.springsecurity.utils.helpers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JWTUtils {

    @Value("${security.jwt.secret.key}")
    private String secretKey;

    @Value("${security.jwt.generator.user}")
    private String generatorUser;

    public String generateToken(Authentication authentication) {

        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
        String username = authentication.getPrincipal().toString();

        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return JWT.create()
                .withIssuer(this.generatorUser)
                .withSubject(username)
                .withClaim("authorities", authorities)
                .withIssuedAt(new Date())
                .withExpiresAt((new Date(System.currentTimeMillis() + 1800000)))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);
    }

    public DecodedJWT verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.generatorUser)
                    .build();

            return verifier.verify(token);

        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(e.getMessage());
        }
    }

    public String getSubject(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    Claim getClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }

    Map<String, Claim> getClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }
}
