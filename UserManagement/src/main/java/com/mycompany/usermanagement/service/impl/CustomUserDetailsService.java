package com.mycompany.usermanagement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.usermanagement.entities.User;
import com.mycompany.usermanagement.entities.UserRole;
import com.mycompany.usermanagement.repository.UserRepository;

/**
 *
 * @author developer
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	// create test users
    	if(userRepository.count() == 0) {
    		List<UserRole> userRoles1 = new ArrayList<>();
    		userRoles1.add(new UserRole("user1", "ROLE_TRUSTED_CLIENT"));
    		userRepository.save(new User("user1", passwordEncoder.encode("password1"), userRoles1, true));
    		
    		List<UserRole> userRoles2 = new ArrayList<>();
    		userRoles2.add(new UserRole("user2", "USER"));
    		userRepository.save(new User("user2", passwordEncoder.encode("password2"), userRoles2, true));
    	}
    	
        return userRepository
                .findByUsername(username)
                .map(u -> new org.springframework.security.core.userdetails.User(
	                u.getUsername(),
	                u.getPassword(),
	                u.isActive(),
	                u.isActive(),
	                u.isActive(),
	                u.isActive(),
	                AuthorityUtils.createAuthorityList( 
	                		u.getUserRoles().stream()
	                			.map(r->r.getRole())
	                			.collect(Collectors.toList())
	                			.toArray(new String[0])
	                )
	             ))
                .orElseThrow(() -> new UsernameNotFoundException("No user with "
                        + "the name " + username + "was found in the database"));
    }

}
