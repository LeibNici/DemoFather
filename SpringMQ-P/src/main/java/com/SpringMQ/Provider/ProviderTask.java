package com.SpringMQ.Provider;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.channels.Pipe;

@Component
public class ProviderTask {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        amqpAdmin.declareQueue(new Queue("my-queue"));
        amqpAdmin.declareExchange(new DirectExchange("my-exchange"));
        amqpAdmin.declareBinding(new Binding("my-queue", Binding.DestinationType.QUEUE,"my-exchange","my-route",null));
    }

    @Scheduled(fixedDelay = 1000)
    public void tssk(){
        rabbitTemplate.convertAndSend("myroute","123");
    }

}
