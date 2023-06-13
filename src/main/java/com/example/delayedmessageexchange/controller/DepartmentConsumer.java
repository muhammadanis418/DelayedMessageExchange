package com.example.delayedmessageexchange.controller;

import com.example.delayedmessageexchange.entity.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DepartmentConsumer {

    private AmqpTemplate amqpTemplate;

    public DepartmentConsumer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @RabbitListener(queues = "delay_test_queue")
    public void recieveMessageFromQueue(Department dept) {

//        amqpTemplate.receive("delay_test_queue", 50000);
        // dept.wait(5000);

//        MessageProperties properties = new MessageProperties();
//        properties.setDelay(50000);
        //  Thread.sleep(5000);
        log.info("##########");
        amqpTemplate.receive("delay_test_queue",50000);
        log.info("********");
        log.info("Message Revcieved: {}", dept);
    }
}
