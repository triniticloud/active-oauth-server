/*
 * (C) Copyright 2016 Active Intelligence Pte Ltd (http://active.ai/).
 *
 * This software is the confidential and proprietary information of Active Intelligence.
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with Active
 * Intelligence
 *
 */
package com.active.oauth.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Shashank Sh.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileResponse implements Serializable {

  @JsonProperty("userId")
  private String userId;

  @JsonProperty("username")
  private String username;

  @JsonProperty("emailId")
  private String emailId;

  @JsonProperty("voicePin")
  private String voicePin;

  @JsonProperty("customerName")
  private String customerName;

  @JsonProperty("mobileNumber")
  private String mobileNumber;

  @JsonProperty("address")
  private String address;

  public ProfileResponse() {
  }

  public ProfileResponse(String userId, String username, String emailId, String voicePin) {
    this.userId = userId;
    this.username = username;
    this.emailId = emailId;
    this.voicePin = voicePin;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmailId() {
    return emailId;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public String getVoicePin() {
    return voicePin;
  }

  public void setVoicePin(String voicePin) {
    this.voicePin = voicePin;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

}
