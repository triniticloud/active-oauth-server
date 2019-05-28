package com.active.oauth.controllers;

import com.active.oauth.model.ProfileResponse;
import com.active.oauth.model.UserPreferences;
import com.active.oauth.repository.UserPreferencesRepository;
import com.active.oauth.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;

/**
 * @author Pritesh Soni
 *
 */
@Controller
public class ProfileController {

  @Value("${security.oauth2.resource.token-info-uri}")
  private String CHECK_TOKEN_URL;

  @Autowired(required = false)
  private UsersRepository usersRepository;

  @Autowired
  private UserPreferencesRepository userPreferencesRepository;

  @RequestMapping(value = "/profile", produces = "application/json")
  @ResponseBody
  public ProfileResponse getProfileInfo(@RequestParam(value = "access_token", required = false) String accessToken,
      @RequestHeader("Authorization") String authorization, Authentication authentication) throws Exception {
    Logger logger = LoggerFactory.getLogger(ProfileController.class);
    logger.debug("OAUTH2: Entered ProfileController -> getProfileInfo");
    if (accessToken == null) {
      String token = authorization.substring(7);
      logger.debug(String.format("OAUTH2 -> token is %s", token));
      return getUserInfo(token);
    } else {
      return getUserInfo(accessToken);
    }
  }

  private ProfileResponse getUserInfo(String accessToken) throws IOException {
    Logger logger = LoggerFactory.getLogger(ProfileController.class);
    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();
    String url = CHECK_TOKEN_URL + "?" + "token=" + accessToken;
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> entity = new HttpEntity<>(headers);
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    String restCall = response.getBody();
    if (restCall != null && !"".equals(restCall)) {
      JsonObject jsonResponse = new com.google.gson.JsonParser().parse(restCall).getAsJsonObject();
      logger.debug(MessageFormat.format("{0} {1}", "OAUTH2: User Name after Parsing ->", jsonResponse.get("user_name").getAsString()));
      String userStr = jsonResponse.get("user_name").getAsString();
      if (!StringUtils.isEmpty(userStr)) {
        UserPreferences userPreferences = objectMapper.readValue(userStr, UserPreferences.class);
        return fetchDetails(userPreferences.getUserId());
      }
    }
    return null;
  }

  private ProfileResponse fetchDetails(String userName) {
    Logger logger = LoggerFactory.getLogger(ProfileController.class);
    Optional<UserPreferences> userPreferences = userPreferencesRepository.findById(userName);
    if (userPreferences.isPresent()) {
      String email = userPreferences.get().getEmailId();
      String pin = userPreferences.get().getVoicePin();
      ProfileResponse profileResponse =
          new ProfileResponse(userPreferences.get().getUserId(), userPreferences.get().getUsername(), email, pin);
      profileResponse.setCustomerName(userPreferences.get().getCustomerName());
      profileResponse.setMobileNumber(userPreferences.get().getMobileNumber());
      profileResponse.setAddress(userPreferences.get().getAddress());
      return profileResponse;
    } else {
      logger.error("OAUTH2: User not present with user_id -> " + userName);
      return null;
    }
  }

}
