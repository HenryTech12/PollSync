package com.votingapi.demo.principal;

import com.votingapi.demo.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class UserPrincipal implements UserDetails {

    private final UserData userData;

    public UserPrincipal(UserData userData) {
        this.userData = userData;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return userData.getPassword();
    }

    @Override
    public String getUsername() {
        return userData.getUsername();
    }
}
