package com.SpringMQ.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chenming
 * @description
 * @create: 2022-03-04
 */
@Component
@Slf4j
public class Sender {

    public static RabbitTemplate static_rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        Sender.static_rabbitTemplate = rabbitTemplate;
    }

    public static void sendMsg(String message) {
        static_rabbitTemplate.convertAndSend(MQconfig.MY_ECXCHANGE, MQconfig.MY_ROUTE, message);
    }

    public static void sendMsg(String exchange, String routeKey, Object msg) {
        static_rabbitTemplate.convertAndSend(exchange, routeKey, msg);
        static_rabbitTemplate.setMandatory(true);
        static_rabbitTemplate.setConfirmCallback(confirmCallback);
        static_rabbitTemplate.setReturnsCallback(returnsCallback);
    }

    static final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            log.info("ack  "+ack);
            if (ack){
                log.warn("消息发送成功");
            }else {
                log.warn("消息发送失败");
            }
        }
    };

    static final RabbitTemplate.ReturnsCallback returnsCallback  = new RabbitTemplate.ReturnsCallback() {
        @Override
        public void returnedMessage(ReturnedMessage returnedMessage) {
            log.info(String.valueOf(returnedMessage.getReplyCode()));
        }
    };

}
