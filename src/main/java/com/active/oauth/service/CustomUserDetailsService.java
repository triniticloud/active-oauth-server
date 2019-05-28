package com.active.oauth.service;

import com.active.oauth.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author Pritesh Soni
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UsersRepository usersRepository;

  private static final List<GrantedAuthority> AUTHORITIES = new ArrayList<>();

  static {
    AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
    AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
  }
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return User.withUsername("abc").password("{noop}abc").roles("USER").build();
  }

}
