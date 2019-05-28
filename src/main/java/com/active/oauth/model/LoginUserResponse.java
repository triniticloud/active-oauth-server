package com.active.oauth.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pritesh Soni
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginUserResponse implements Serializable {

  @JsonProperty("user")
  private CustomerProfile customerProfile;

  private Map<String, String> result = new HashMap<>();

  private Map<String, Object> extraParams = new HashMap();

  public CustomerProfile getCustomerProfile() {
    return customerProfile;
  }

  public void setCustomerProfile(CustomerProfile customerProfile) {
    this.customerProfile = customerProfile;
  }

  public Map<String, String> getResult() {
    return result;
  }

  public void setResult(Map<String, String> result) {
    this.result = result;
  }

  public Map<String, Object> getExtraParams() {
    return extraParams;
  }

  public void setExtraParams(Map<String, Object> extraParams) {
    this.extraParams = extraParams;
  }
}
