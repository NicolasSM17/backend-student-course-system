package com.microservice.authentication.config;

import com.microservice.authentication.entity.UserCredential;
import com.microservice.authentication.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserCredentialRepository credentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> userCredential = credentialRepository.findByName(username);

        return userCredential.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado con nombre: " + username));
    }
}
