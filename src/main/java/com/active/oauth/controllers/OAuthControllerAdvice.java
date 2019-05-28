package com.active.oauth.controllers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

/**
 * @author Pritesh Soni
 *
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequestMapping(produces = "application/vnd.error+json")
public class OAuthControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
  public ResponseEntity<VndErrors> internalServerException(final HttpServerErrorException.InternalServerError e) {
    return error(e, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
  }

  @ExceptionHandler(OAuth2Exception.class)
  public ResponseEntity<VndErrors> oAuth2Exception(final OAuth2Exception e) {
    return error(e, HttpStatus.PRECONDITION_FAILED, e.getSummary());
  }

  @ExceptionHandler(NoSuchClientException.class)
  public ResponseEntity<VndErrors> NoSuchClientException(final NoSuchClientException e) {
    return error(e, HttpStatus.PRECONDITION_FAILED, e.getMessage());
  }

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<VndErrors> nullPointerException(final NullPointerException e) {
    return error(e, HttpStatus.INTERNAL_SERVER_ERROR, "Resource not found");
  }

  private ResponseEntity <VndErrors> error(final Exception exception, final HttpStatus httpStatus, final String logRef) {
    final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
    return new ResponseEntity < > (new VndErrors(logRef, message), httpStatus);
  }

}
