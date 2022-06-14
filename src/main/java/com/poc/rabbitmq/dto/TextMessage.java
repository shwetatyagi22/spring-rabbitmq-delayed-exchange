package com.poc.rabbitmq.dto;

/**
 * @author shweta.tyagi
 * 2022-06-08 at 5:39 PM
 */
public class TextMessage {
  private String text = "Hello";

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
