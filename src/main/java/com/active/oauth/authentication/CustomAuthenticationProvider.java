package com.active.oauth.authentication;

import com.active.oauth.model.CustomerProfile;
import com.active.oauth.model.LoginUserRequest;
import com.active.oauth.model.LoginUserResponse;
import com.active.oauth.model.UserPreferences;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Value("${application.login.url}")
  private String applicationLoginUrl;

  private static final List<GrantedAuthority> AUTHORITIES = new ArrayList<>();

  static {
    AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
    AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    try {
      logger.debug("OAUTH2: Reached login");
      ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
      Collections.list(attr.getRequest().getParameterNames()).stream().filter(s -> !s.equalsIgnoreCase("username") || !s.equalsIgnoreCase(
              "password")).forEach(s -> attr.getRequest().getSession().setAttribute(s, attr.getRequest().getParameter(s)));
      LoginUserRequest loginUserRequest = getLoginUserRequest(authentication);
      RestTemplate restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "application/json");
      HttpEntity<LoginUserRequest> entity = new HttpEntity<LoginUserRequest>(loginUserRequest, headers);
      ResponseEntity<String> loginResponse = restTemplate.exchange(applicationLoginUrl, HttpMethod.POST, entity, String.class);
      Authentication userPreferencesStr = processResponse(loginUserRequest, loginResponse);
      if (userPreferencesStr != null) {
        return userPreferencesStr;
      }
    } catch (Exception e) {
      logger.error("OAUTH2: API error in CustomAuthenticationProvider", e);
    }
    return null;
  }

  private Authentication processResponse(LoginUserRequest loginUserRequest,
                                         ResponseEntity<String> loginResponse) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    if (loginResponse.getStatusCode() == HttpStatus.OK && null != loginResponse.getBody()) {
      LoginUserResponse loginUserResponse = mapper.readValue(loginResponse.getBody(), LoginUserResponse.class);
      if (loginUserResponse != null && loginUserResponse.getResult() != null && 200 == Integer
              .parseInt(loginUserResponse.getResult().get("status"))) {
        CustomerProfile customerProfile = loginUserResponse.getCustomerProfile();
        String customerId = customerProfile.getCustomerId();
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setUserId(customerId);
        userPreferences.setUsername(loginUserRequest.getUserID());
        if (!StringUtils.isEmpty(customerProfile.getCustomerName())) {
          userPreferences.setCustomerName(customerProfile.getCustomerName());
        }
        if (!StringUtils.isEmpty(customerProfile.getMobileNumber())) {
          userPreferences.setMobileNumber(customerProfile.getMobileNumber());
        }
        if (!StringUtils.isEmpty(customerProfile.getAddress())) {
          userPreferences.setAddress(customerProfile.getAddress());
        }
        if (!StringUtils.isEmpty(customerProfile.getEmailId())) {
          userPreferences.setEmailId(customerProfile.getEmailId());
        }
        String userPreferencesStr = mapper.writeValueAsString(userPreferences);
        return new UsernamePasswordAuthenticationToken(userPreferencesStr, loginUserRequest.getPassword(), AUTHORITIES);
      }
    } else if (loginResponse.getStatusCode() == HttpStatus.UNAUTHORIZED && null != loginResponse.getBody()) {
      LoginUserResponse loginUserResponse = mapper.readValue(loginResponse.getBody(), LoginUserResponse.class);
      if (loginUserResponse != null && loginUserResponse.getResult() != null && 401 == Integer
              .parseInt(loginUserResponse.getResult().get("status"))) {
        throw new BadCredentialsException("Bad credentials");
      }
    } else {
      throw new Exception("Login response is empty");
    }
    return null;
  }

  private LoginUserRequest getLoginUserRequest(Authentication authentication) {
    LoginUserRequest loginUserRequest = new LoginUserRequest();
    loginUserRequest.setUserID(authentication.getName());
    loginUserRequest.setPassword(authentication.getCredentials().toString());
    return loginUserRequest;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }

}
