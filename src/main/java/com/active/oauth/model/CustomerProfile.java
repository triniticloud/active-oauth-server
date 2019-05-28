package com.active.oauth.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 *
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerProfile implements Serializable {

  @JsonProperty("customerId")
  private String customerId;

  @JsonProperty("customerName")
  private String customerName;

  @JsonProperty("mobileNumber")
  private String mobileNumber;

  @JsonProperty("emailId")
  private String emailId;

  @JsonProperty("address")
  private String address;

  public CustomerProfile() {
  }

  public CustomerProfile(String customerId, String customerName, String mobileNumber, String emailId, String address) {
    this.customerId = customerId;
    this.customerName = customerName;
    this.mobileNumber = mobileNumber;
    this.emailId = emailId;
    this.address = address;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
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

  public String getEmailId() {
    return emailId;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
