package com.astrapay.exception;

/**
 * This exception is used when data is not retrieved from data storage
 * Always returning 404 Http status
 */

public class DataNotFoundException extends Exception {
  public DataNotFoundException(String message) {
    super(message);
  }
}
