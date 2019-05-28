package com.active.oauth.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Pritesh Soni
 *
 */
@Entity
@Table(name = "user_preferences", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id"}))
public class UserPreferences implements Serializable {

  @Id
  @Column(name = "user_id")
  private String userId;

  @Column(name = "username")
  private String username;

  @Column(name = "email_id")
  private String emailId;

  @Column(name = "voice_pin")
  private String voicePin;

  @Column(name = "customer_name")
  private String customerName;

  @Column(name = "mobile_number")
  private String mobileNumber;

  @Column(name = "address")
  private String address;

  public UserPreferences() {
  }

  public UserPreferences(String userId, String username, String emailId, String voicePin) {
    this.userId = userId;
    this.username = username;
    this.emailId = emailId;
    this.voicePin = voicePin;
  }

  public UserPreferences(String userId, String username, String emailId, String voicePin, String customerName, String mobileNumber,
      String address) {
    this.userId = userId;
    this.username = username;
    this.emailId = emailId;
    this.voicePin = voicePin;
    this.customerName = customerName;
    this.mobileNumber = mobileNumber;
    this.address = address;
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
