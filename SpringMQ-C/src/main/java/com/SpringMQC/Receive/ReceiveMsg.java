package com.SpringMQC.Receive;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Return;
import com.rabbitmq.client.ReturnCallback;
import com.sun.org.apache.xpath.internal.operations.String;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author chenming
 * @description
 * @create: 2022-03-04
 */
@Component
@Slf4j
public class ReceiveMsg {

//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue,
//            exchange = @Exchange(type = "topic", name = "my.exchange"),
//            key = "my.route.1"
//    ))
//    @RabbitHandler
//    public void receive1(String message) {
//        log.info("消费者1" + message);
//    }
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue,
//            exchange = @Exchange(type = "topic", name = "my.exchange"),
//            key = "my.route.2"
//    ))
//    @RabbitHandler
//    public void receive2(String message) {
//        log.info("消费者1" + message);
//    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(type = "topic", name = "my.exchange"),
            key = "my.route.0"
    ))
    @RabbitHandler
    public void receive3(Message message, Channel channel) throws IOException {
        log.info("消费者1" + message.getBody());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
