package com.active.oauth.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Map;

@Controller
@SessionAttributes("authorizationRequest")
public class AuthorizeController {
  @Value("${custom.application.domain}")
  private String domain;

  @Value("${custom.application.protocol}")
  private String scheme;

  @Value("${custom.application.context}")
  private String contextPath;

  @RequestMapping("/oauth/confirm_access")
  public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) {
    AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
    ModelAndView view = new ModelAndView();
    view.setViewName("authorize");
    //view.addObject("clientId", authorizationRequest.getClientId());
    view.addObject("authorizeUrl", MessageFormat.format("{0}://{1}{2}/oauth/authorize", scheme, domain, !StringUtils.isEmpty(contextPath) ?
            "/" + contextPath : ""));
    view.addAllObjects(request.getParameterMap());
    return view;
  }
}