package com.example.delayedmessageexchange.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

@Configuration
//@EnableAutoConfiguration(exclude = RabbitAutoConfiguration.class)
public class RabbitConfig {

    public final static String DELAY_TEST_EXCHANGE = "delay_test_exchange";
    public final static String DELAY_TEST_QUEUE = "delay_test_queue";
    public final static String DELAY_TEST_ROUTING_QUEUE = "to_delay_test_queue";

    private final ObjectMapper objectMapper;

    public RabbitConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    CustomExchange testDelayDirect() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAY_TEST_EXCHANGE, "x-delayed-message", true, false, args);
    }

    @Bean
    public Queue queue() {
        return new Queue(DELAY_TEST_QUEUE);

    }

    @Bean
    public Binding testDelayBinding(CustomExchange testDelayDirect, Queue testDelayQueue) {
        return BindingBuilder.bind(testDelayQueue).to(testDelayDirect).with(DELAY_TEST_ROUTING_QUEUE).noargs();
    }

    //    @Bean
//    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(jsonMessageConvertor());
//        return rabbitTemplate;
//    }
    @Bean
  @Primary
    public AmqpTemplate rabbitTemplatey(ConnectionFactory factory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(jsonMessageConvertor());
        return rabbitTemplate;
    }


    @Bean
    public MessageConverter jsonMessageConvertor() {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
