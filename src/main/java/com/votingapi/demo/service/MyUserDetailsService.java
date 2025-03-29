package com.votingapi.demo.service;

import com.votingapi.demo.model.UserData;
import com.votingapi.demo.principal.UserPrincipal;
import com.votingapi.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserData> optionalUserData = userRepository.findByUsername(username);
        System.out.println("user details found");

        return new UserPrincipal(optionalUserData
                    .orElseThrow(() -> new UsernameNotFoundException("user not found")));
    }
}
