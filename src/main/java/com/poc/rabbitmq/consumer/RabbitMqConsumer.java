package com.poc.rabbitmq.consumer;

import com.poc.rabbitmq.config.Constant;
import com.poc.rabbitmq.dto.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author shweta.tyagi
 * 2022-06-14 at 6:41 PM
 */
@Slf4j
public class RabbitMqConsumer {

  @RabbitListener(queues = Constant.DELAY_TEST_QUEUE)
  public void consumeDataRefinementMessages(TextMessage messageDto) {
    log.info("Consuming Message: {}",
        messageDto.getText());
  }

}
