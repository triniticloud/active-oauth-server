package com.active.oauth.config.mysql;

import com.active.oauth.authentication.CustomAuthenticationProvider;
import com.active.oauth.config.ContinueEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

/**
 * @author Pritesh Soni
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class OAuth2SecurityConfigurationMysql extends WebSecurityConfigurerAdapter {

  @Bean
  public AuthenticationManager customAuthenticationManager() throws Exception {
    return authenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Autowired
  private CustomAuthenticationProvider customAuthenticationProvider;

  @Autowired
  private UserDetailsService customUserDetailsService;

  private AuthenticationManager authenticationManager;

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/resources/");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers("/").permitAll()
            .and().exceptionHandling()
            .authenticationEntryPoint(new ContinueEntryPoint("/login"))
            .and().formLogin()
            .loginPage("/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .successForwardUrl("/renderpin")
            .failureForwardUrl("/loginerror");
    http.csrf().disable();
    http.headers().frameOptions().disable();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationMgr) throws Exception {
    /*
     *   The authentication manager here currently uses customUserDetailsService from the database.
     *   This needs to be overridden by client API calls, camel routes or any other fulfilment services for authentication
     **/
    authenticationMgr.parentAuthenticationManager(authenticationManager()).authenticationProvider(customAuthenticationProvider);
  }

  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return new ProviderManager(Collections.singletonList(customAuthenticationProvider));
  }

}
