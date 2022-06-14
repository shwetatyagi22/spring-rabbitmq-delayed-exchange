package com.poc.rabbitmq.test;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqPublisher {
  private AmqpTemplate amqpTemplate;

  public RabbitMqPublisher(AmqpTemplate amqpTemplate) {
    this.amqpTemplate = amqpTemplate;
  }

  @Scheduled(cron = "0 0/1 * * * ?")
  public void scheduleMessageToQueue() {
    TextMessage textMessage = new TextMessage();
    long delay = 30000;
    textMessage.setText("Hello, Text"+delay);
    sendMessage(textMessage, delay);
  }

  public void sendMessage(TextMessage textMessageToSend, final long delayTimes) {
    //Send message to delay queue
    amqpTemplate.convertAndSend("delay_test_exchange", "delay_test_queue", textMessageToSend,
        message -> {
          message.getMessageProperties().setHeader("x-delay", delayTimes);
          return message;
        });
  }
}
