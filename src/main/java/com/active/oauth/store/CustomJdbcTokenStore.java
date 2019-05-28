package com.active.oauth.store;

import com.active.oauth.model.UserPreferences;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author Pritesh Soni
 *
 */
public class CustomJdbcTokenStore extends JdbcTokenStore {

  private final JdbcTemplate jdbcTemplate;

  private Logger logger = LoggerFactory.getLogger(CustomJdbcTokenStore.class);

  private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

  private static final String insertAccessTokenSql =
      "insert into oauth_access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) values (?, ?, ?, ?, ?, ?, ?)";

  public CustomJdbcTokenStore(DataSource dataSource) {
    super(dataSource);
    Assert.notNull(dataSource, "DataSource required");
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
    String refreshToken = null;
    if (token.getRefreshToken() != null) {
      refreshToken = token.getRefreshToken().getValue();
    }
    if (this.readAccessToken(token.getValue()) != null) {
      this.removeAccessToken(token.getValue());
    }
    String userPreferencesStr = (String) authentication.getPrincipal();
    ObjectMapper objectMapper = new ObjectMapper();
    UserPreferences savedUserPreferences = null;
    try {
      savedUserPreferences = objectMapper.readValue(userPreferencesStr, UserPreferences.class);
    } catch (IOException e) {
      logger.error("CustomJdbcTokenStore: Failed while deserializing UserPreferencesStr at line no 59", e);
    }
    this.jdbcTemplate.update(insertAccessTokenSql,
        new Object[] {this.extractTokenKey(token.getValue()), new SqlLobValue(this.serializeAccessToken(token)),
            this.authenticationKeyGenerator.extractKey(authentication),
            savedUserPreferences != null ? savedUserPreferences.getUserId() : null, authentication.getOAuth2Request().getClientId(),
            new SqlLobValue(this.serializeAuthentication(authentication)), this.extractTokenKey(refreshToken)},
        new int[] {12, 2004, 12, 12, 12, 2004, 12});
  }

}
