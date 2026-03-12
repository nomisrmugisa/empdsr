package org.pdsr.security;

import org.pdsr.dhis2.DHIS2AuthService;
import org.pdsr.dhis2.DHIS2User;
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

    @Autowired
    private DHIS2AuthService dhis2AuthService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<DHIS2User> dhis2User = dhis2AuthService.authenticate(username, password);

        if (dhis2User.isPresent()) {
            // For now, give a default role. In Phase 2, we can refine this.
            return new UsernamePasswordAuthenticationToken(
                    username, 
                    password, 
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
        } else {
            throw new BadCredentialsException("DHIS2 Authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
