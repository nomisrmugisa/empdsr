package org.pdsr.security;

import org.pdsr.dhis2.DHIS2AuthService;
import org.pdsr.dhis2.DHIS2User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DHIS2AuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(DHIS2AuthenticationProvider.class);

    @Autowired
    private DHIS2AuthService dhis2AuthService;

    @Autowired
    private org.pdsr.master.repo.UserTableRepository userRepo;

    @Autowired
    private org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        logger.info("Processing DHIS2 login request for user: {}", username);

        Optional<DHIS2User> dhis2User = Optional.empty();
        try {
            dhis2User = dhis2AuthService.authenticate(username, password);

            if (dhis2User.isPresent()) {
                logger.info("Successfully authenticated user: {} via DHIS2. Updating local cache.", username);
                
                // Upsert user into local database for offline fallback
                org.pdsr.master.model.user_table localUser = userRepo.findById(username)
                        .orElse(new org.pdsr.master.model.user_table());
                
                localUser.setUsername(username);
                localUser.setPassword(passwordEncoder.encode(password)); // Cache hashed password
                localUser.setEnabled(true);
                localUser.setUserfullname(dhis2User.get().getDisplayName());
                if (localUser.getUseremail() == null) localUser.setUseremail(username + "@dhis2.org");
                if (localUser.getUsercontact() == null) localUser.setUsercontact("DHIS2-" + dhis2User.get().getId());
                localUser.setAlerted(false);

                // Cache Organisation Units JSON for offline facility selection
                try {
                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    String unitsJson = mapper.writeValueAsString(dhis2User.get().getOrganisationUnits());
                    localUser.setUnitsJson(unitsJson);
                } catch (Exception e) {
                    logger.error("Failed to serialize OrgUnits for user: {}", username, e);
                }
                
                userRepo.save(localUser);

                // Load the user's existing group roles from the local DB so that
                // DHIS2-authenticated users get the same permissions as local users
                Set<GrantedAuthority> authorities = new HashSet<>();
                org.pdsr.master.model.user_table savedUser = userRepo.findById(username)
                        .orElse(localUser);
                for (org.pdsr.master.model.group_table g : savedUser.getGroups()) {
                    authorities.add(new SimpleGrantedAuthority(g.getGroup_role()));
                }
                if (authorities.isEmpty()) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                }

                return new UsernamePasswordAuthenticationToken(username, password, authorities);
            }
        } catch (Exception e) {
            logger.error("DHIS2 Server unreachable or error occurred for user: {}. Falling back to local auth if available.", username, e);
            // Returning null allows Spring Security to try the next AuthenticationProvider (Local DB)
            return null; 
        }

        if (!dhis2User.isPresent()) {
            logger.warn("DHIS2 authentication explicitly failed for user: {}. Will try local fallback.", username);
            return null; // Returning null allows Spring Security to try the next AuthenticationProvider (Local DB)
        }

        return null; // Should not reach here
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
