package com.active.oauth.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

/**
 * @author Pritesh Soni
 */
@Controller
public class LoginController {
  Logger logger = LoggerFactory.getLogger(LoginController.class);

  @Value("${custom.application.domain}")
  private String domain;

  @Value("${custom.application.protocol}")
  private String scheme;

  @Value("${custom.application.context}")
  private String contextPath;

  public class RedirectModel {
    @Pattern(regexp = "^/([^/].*)?$")
    @NotBlank
    private String continueUrl;

    public void setContinue(String continueUrl) {
      this.continueUrl = continueUrl;
    }

    public String getContinue() {
      return continueUrl;
    }
  }

  @RequestMapping(value = "/oauthLogin", method = RequestMethod.GET)
  public ModelAndView getLoginForm(@ModelAttribute("loginError") String loginError, HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("oauthLogin");
    mav.addObject("loginUrl", MessageFormat.format("{0}://{1}{2}/login", scheme, domain, !StringUtils.isEmpty(contextPath) ?
            "/" + contextPath : ""));
    mav.addObject("botCode", request.getSession().getAttribute("botCode"));
    mav.addObject("botDomain", request.getSession().getAttribute("botDomain"));
    mav.addObject("redirect_uri", request.getSession().getAttribute("redirect_uri"));
    return mav;
  }

  @RequestMapping("/login")
  public ModelAndView getLoginView(final HttpServletRequest request, final HttpServletResponse response,
                                   @Valid @ModelAttribute RedirectModel model) {
    ModelAndView view = new ModelAndView();
    String serverName = request.getServerName();
    int serverPort = request.getServerPort() != 80 ? request.getServerPort() : 0;
    String redirectUrl = MessageFormat.format("{0}://{1}{2}{3}{4}", scheme, serverName, serverPort != 0 ? ":" + serverPort : "",
            !StringUtils.isEmpty(request.getContextPath()) ? request.getContextPath() : "", model.getContinue());
    Map<String, List<String>> parameterMap = splitQuery(redirectUrl);
    final String botCode = parameterMap.get("botCode").get(0);
    final String botDomain = parameterMap.get("botDomain").get(0);
    request.getSession().setAttribute("botCode", botCode);
    request.getSession().setAttribute("botDomain", botDomain);
    request.getSession().setAttribute("redirect_uri", redirectUrl);
    view.getModelMap().put("botCode",botCode);
    view.getModelMap().put("botDomain",botDomain);
    view.setViewName(MessageFormat.format("forward:oauthLogin", botCode, botDomain));
    return view;
  }

  @RequestMapping("/loginerror")
  public ModelAndView getLoginErrorView(@ModelAttribute("loginError") String loginError, final HttpServletRequest request, final HttpServletResponse response) throws JsonParseException{
    ModelAndView view = new ModelAndView();
    view.addObject("loginUrl", MessageFormat.format("{0}://{1}{2}/login", scheme, domain, !StringUtils.isEmpty(contextPath) ?
        "/" + contextPath : ""));
    view.addObject("botCode", request.getSession().getAttribute("botCode"));
    view.addObject("botDomain", request.getSession().getAttribute("botDomain"));
    view.addObject("redirect_uri", request.getSession().getAttribute("redirect_uri"));
    view.addObject("loginError", "loginError");
    view.getModelMap().put("botCode", request.getSession().getAttribute("botCode"));
    view.getModelMap().put("botDomain", request.getSession().getAttribute("botDomain"));
    view.setViewName("oauthLogin");
    return view;
  }
  private Map<String, List<String>> splitQuery(String url) {
    return Arrays.stream(url.split("&"))
            .map(this::splitQueryParameter)
            .collect(Collectors.groupingBy(AbstractMap.SimpleImmutableEntry::getKey, LinkedHashMap::new, mapping(Map.Entry::getValue,
                    toList())));
  }

  private AbstractMap.SimpleImmutableEntry<String, String> splitQueryParameter(String it) {
    final int idx = it.indexOf("=");
    final String key = idx > 0 ? it.substring(0, idx) : it;
    final String value = idx > 0 && it.length() > idx + 1 ? it.substring(idx + 1) : null;
    return new AbstractMap.SimpleImmutableEntry<>(key, value);
  }

}
