package com.active.oauth.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
@Service
public class OAuthUserDetailsService implements UserDetailsService {

  private static final List<GrantedAuthority> AUTHORITIES = new ArrayList<>();

  static {
    AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
    AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return new User(username, "", AUTHORITIES);
  }

}
