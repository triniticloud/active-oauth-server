package com.active.oauth.controllers;

import com.active.oauth.model.ProfileResponse;
import com.active.oauth.model.UserPreferences;
import com.active.oauth.model.VoicePin;
import com.active.oauth.repository.UserPreferencesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author Pritesh Soni
 */
@Controller
public class OnboardingController {

  @Autowired
  private UserPreferencesRepository userPreferencesRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @RequestMapping(value = "/renderpin", method = RequestMethod.POST)
  public ModelAndView getVoicePinFormPath(Authentication authentication, Map<String, Object> model, HttpServletRequest request) throws IOException {
    ModelAndView view;
    if (authentication != null) {
      view = getRedirectionView(authentication, request);
      if (view != null) {
        return view;
      }
    }
    return getModelAndView(request, "pin");
  }

  @RequestMapping(value = "/pin", method = RequestMethod.GET)
  public String getVoicePinForm() {
    return "pin";
  }

  @RequestMapping(value = "/voicepin", method = RequestMethod.POST)
  public ModelAndView saveVoicePin(@ModelAttribute(name = "voicepin") VoicePin voicePin, Authentication authentication,
                                   HttpServletRequest request) throws IOException {
    ModelAndView view;
    if (voicePin != null && !StringUtils.isEmpty(voicePin.getVoicePin())) {
      request.getSession().setAttribute("voicepin", voicePin.getVoicePin());
    }
    if (authentication != null) {
      view = getRedirectionView(authentication, request);
      if (view != null) {
        return view;
      }
    }
    return getModelAndView(request, "pref");
  }

  @RequestMapping(value = "/pref")
  public String getPreferencesForm() {
    return "pref";
  }

  @RequestMapping(value = "/savepref", method = RequestMethod.POST)
  public String savePreferences(@ModelAttribute(name = "ProfileResponse") ProfileResponse profileResponse, @ModelAttribute(name = "pref") String pref, Authentication authentication,
                                HttpServletRequest request) throws IOException {
    Logger logger = LoggerFactory.getLogger(OnboardingController.class);
    String userPreferencesStr = (String) authentication.getPrincipal();
    UserPreferences savedUserPreferences = objectMapper.readValue(userPreferencesStr, UserPreferences.class);
    HttpSession session = request.getSession();
    String savedVoicePin = (String) session.getAttribute("voicepin");
    Optional<UserPreferences> existingUserPreferences = userPreferencesRepository.findById(savedUserPreferences.getUserId());
    if (existingUserPreferences.isPresent()) {
      UserPreferences newUserPreferences = existingUserPreferences.get();
      newUserPreferences.setVoicePin(savedVoicePin);
      newUserPreferences.setEmailId(pref);
      userPreferencesRepository.save(newUserPreferences);
    } else {
      logger.debug("OAUTH2: Saved voice pin -> " + savedVoicePin);
      savedUserPreferences.setEmailId(pref);
      savedUserPreferences.setVoicePin(savedVoicePin);
      userPreferencesRepository.save(savedUserPreferences);
    }
    String redirectUrl = getRedirectUrl(session);
    return MessageFormat.format("redirect:{0}", redirectUrl);
  }

  private String getRedirectUrl(HttpSession session) {
    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
    ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
    HttpServletRequest request = attributes.getRequest();
    HttpSession httpSession = request.getSession(false);

    Object redirect_uri = httpSession.getAttribute("redirect_uri");
    System.out.println("redirect_uri ------> "+redirect_uri);
    return session.getAttribute("redirect_uri").toString();
  }

  private ModelAndView getRedirectionView(Authentication authentication, HttpServletRequest request) throws IOException {
    System.out.println("Session attributes " + Collections.list(request.getSession().getAttributeNames()));
    String userPreferencesStr = (String) authentication.getPrincipal();
    UserPreferences savedUserPreferences = objectMapper.readValue(userPreferencesStr, UserPreferences.class);
    Optional<UserPreferences> userPreferences = userPreferencesRepository.findById(savedUserPreferences.getUserId());
    if (userPreferences.isPresent()) {
      ModelAndView view = new ModelAndView();
      HttpSession session = request.getSession();
      view.setViewName(MessageFormat.format("redirect:{0}", getRedirectUrl(session)));
      return view;
    }
    return null;
  }

  private ModelAndView getModelAndView(HttpServletRequest request, String viewName) {
    System.out.println("Session attributes getModelAndView " + Collections.list(request.getSession().getAttributeNames()));
    ModelAndView view;
    final String botCode = (String) request.getSession().getAttribute("botCode");
    final String botDomain = (String) request.getSession().getAttribute("botDomain");
    view = new ModelAndView();
    view.setViewName(MessageFormat.format("redirect:{0}?botCode={1}&botDomain={2}", viewName, botCode, botDomain));
    return view;
  }

}
