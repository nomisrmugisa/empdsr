package org.pdsr;

import java.util.HashSet;
import java.util.Set;

import org.pdsr.model.group_table;
import org.pdsr.model.user_table;
import org.pdsr.repo.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserTableRepository api;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!api.findById(username).isPresent()) {
            throw new UsernameNotFoundException(username);
        }

        final user_table json = api.findById(username).get();
        
        if(!json.isEnabled()) {
            throw new UsernameNotFoundException(username);        	
        }
        
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (group_table elem : json.getGroups()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(elem.getGroup_role()));

        }

        return new User(json.getUsername(), json.getPassword(), json.isEnabled(),true,true,true, grantedAuthorities);

    }

}