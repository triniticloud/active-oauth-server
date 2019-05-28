package com.active.oauth.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author Pritesh Soni
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoicePin implements Serializable {

  private String voicePin;

  public VoicePin(String voicePin) {
    this.voicePin = voicePin;
  }

  public VoicePin() {
  }

  public String getVoicePin() {
    return voicePin;
  }

  public void setVoicePin(String voicePin) {
    this.voicePin = voicePin;
  }
}
