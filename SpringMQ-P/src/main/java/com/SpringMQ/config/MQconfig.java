package com.SpringMQ.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQconfig {

    public static final String MY_QUEUE = "my.queue";
    public static final String MY_ECXCHANGE = "my.exchange";
    public static final String MY_ROUTE = "my.route.#";

    @Bean(MY_ECXCHANGE)
    public TopicExchange topicExchange(){
        return new TopicExchange(MY_ECXCHANGE);
    }

    @Bean(MY_QUEUE)
    public Queue queue(){
        return new Queue(MY_QUEUE);
    }

    @Bean
    public Binding binding(@Qualifier(MY_ECXCHANGE)TopicExchange exchange, @Qualifier(MY_QUEUE)Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(MY_ROUTE);
    }


}
