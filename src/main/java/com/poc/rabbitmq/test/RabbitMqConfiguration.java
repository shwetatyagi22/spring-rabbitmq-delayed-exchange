package com.poc.rabbitmq.test;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {
  private final ObjectMapper objectMapper;

  public RabbitMqConfiguration(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }


  /**
   * Switch bound to message queue of order delay plug-in
   */
  @Bean
  CustomExchange testDelayDirect() {
    Map<String, Object> args = new HashMap<>();
    args.put("x-delayed-type", "direct");
    return new CustomExchange(Constant.DELAY_TEST_EXCHANGE, "x-delayed-message", true, false, args);
  }

  /**
   * Order delay plug in queue
   */
  @Bean
  public Queue testDelayQueue() {
    return new Queue(Constant.DELAY_TEST_QUEUE);
  }

  /**
   * Bind order delay plug-in queue to switch
   */
  @Bean
  public Binding testDelayBinding(CustomExchange testDelayDirect, Queue testDelayQueue) {
    return BindingBuilder
        .bind(testDelayQueue)
        .to(testDelayDirect)
        .with(Constant.DELAY_TEST_ROUTING_QUEUE)
        .noargs();
  }

  @Bean
  public AmqpTemplate rabbitTemplate(ConnectionFactory factory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
    rabbitTemplate.setMessageConverter(jsonMessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter(objectMapper);
  }
}

