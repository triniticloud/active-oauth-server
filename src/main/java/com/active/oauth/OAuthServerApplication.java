package com.active.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 *
 *
 */
@SpringBootApplication
@EnableJpaRepositories("com.active.oauth.repository")
public class OAuthServerApplication extends SpringBootServletInitializer {

  /*
   * 1. Compile the application using -> mvn clean install -DskipTests
   * 2. Mention the profile while running the application:
   *    a. h2 profile ->     mvn -Dspring.profiles.active=h2 spring-boot:run --debug&
   *    b. mysql profile ->  mvn -Dspring.profiles.active=mysql spring-boot:run --debug&
   * NOTE: A database named "oauth" is mandatory before running the application
   */

  public static void main(String[] args) {
    SpringApplication.run(OAuthServerApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(OAuthServerApplication.class);
  }

}
