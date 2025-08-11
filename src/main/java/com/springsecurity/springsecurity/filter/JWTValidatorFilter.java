package com.springsecurity.springsecurity.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.springsecurity.springsecurity.utils.helpers.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JWTValidatorFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    public JWTValidatorFilter(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Get token from headers
        String token = request.getHeader("Authorization");

        // validate token structure. Must init Bearer
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            DecodedJWT decodedJWT = jwtUtils.verifyToken(token);                                // Verify token
            String username = decodedJWT.getSubject();                                          // get sublect
            String claimAuthorities = decodedJWT.getClaim("authorities").asString();        // get authorities

            // Convert string claims to list
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(claimAuthorities);

            // Register user on context holder
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
        }

        filterChain.doFilter(request, response); // This line failed if token is not valid
    }
}
