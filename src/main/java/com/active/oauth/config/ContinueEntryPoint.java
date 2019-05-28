package com.active.oauth.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sriram Kumar
 */
public class ContinueEntryPoint extends LoginUrlAuthenticationEntryPoint {


  public ContinueEntryPoint(String loginFormUrl) {
    super(loginFormUrl);
    super.setForceHttps(true);
  }

  @Override
  protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response,
                                                   AuthenticationException exception) {
    String continueParamValue = UrlUtils.buildRequestUrl(request);
    String redirect = super.determineUrlToUseForThisRequest(request, response, exception);
    request.getSession().setAttribute("continue", continueParamValue);
    return UriComponentsBuilder.fromPath(redirect).queryParam("continue", continueParamValue).toUriString();
  }
}