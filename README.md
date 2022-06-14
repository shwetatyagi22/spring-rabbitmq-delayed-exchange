# Spring boot and RabbitMQ Delayed Exchange
In this repository, I have tried to implement delayed messaging in rabbitmq using spring-boot.

## RabbitMQ Delayed Message Plugin

RabbitMQ does not support delayed messages, we need to add delayed plugin to RabbitMQ to 
enable delayed-messaging (or scheduled-messaging). 
### Installation
You can delayed plugin is available from github (https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases).

To enable the plugin, run the below command:

```
rabbitmq-plugins enable rabbitmq_delayed_message_exchange
```

### Implementation

To use the functionality of scheduled/delayed messages, declare an exchange of type ```x-delayed-message```. Below is the code snippet.
``` @Bean
  CustomExchange testDelayDirect() {
    Map<String, Object> args = new HashMap<>();
    args.put("x-delayed-type", "direct");
    return new CustomExchange(Constant.DELAY_TEST_EXCHANGE, "x-delayed-message", true, false, args);
  } 
  ```

Once exchange is configured, create queue and bind it to delayed exchange using routing key.

```  
  @Bean
  public Queue testDelayQueue() {
    return new Queue(Constant.DELAY_TEST_QUEUE);
  }

  /**
   * Bind delay queue to exchange
   */
  @Bean
  public Binding testDelayBinding(CustomExchange testDelayDirect, Queue testDelayQueue) {
    return BindingBuilder
        .bind(testDelayQueue)
        .to(testDelayDirect)
        .with(Constant.DELAY_TEST_ROUTING_QUEUE)
        .noargs();
  }
```
Now, delayed exchange is ready, we can publish delayed messages to the queue.
