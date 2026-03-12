package org.pdsr.security;

import org.pdsr.dhis2.DHIS2AuthService;
import org.pdsr.dhis2.DHIS2User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class DHIS2AuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(DHIS2AuthenticationProvider.class);

    @Autowired
    private DHIS2AuthService dhis2AuthService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        logger.info("Processing login request for user: {}", username);

        Optional<DHIS2User> dhis2User = dhis2AuthService.authenticate(username, password);

        if (dhis2User.isPresent()) {
            logger.info("Successfully authenticated user: {} via DHIS2", username);
            return new UsernamePasswordAuthenticationToken(
                    username, 
                    password, 
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
        } else {
            logger.warn("Authentication failed for user: {} (DHIS2 returned empty user)", username);
            throw new BadCredentialsException("DHIS2 Authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
