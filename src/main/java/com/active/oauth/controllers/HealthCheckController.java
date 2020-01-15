package com.active.oauth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthCheckController {

  @RequestMapping(value = "/health", method = RequestMethod.GET)
  @ResponseBody
  public String checkOauthHealth(){
    return "Oauth is Alive";
  }
}
