//package com.SpringMQ.config;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class MQconfig {
//
//    @Bean
//    public CachingConnectionFactory cachingConnectionFactory() {
//        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
//        cachingConnectionFactory.setHost("192.168.85.129");
//        cachingConnectionFactory.setPort(5672);
//        cachingConnectionFactory.setUsername("chenming");
//        cachingConnectionFactory.setPassword("chenming");
//        return cachingConnectionFactory;
//    }
//
//    @Bean
//    public RabbitAdmin rabbitAdmin() {
//        RabbitAdmin rabbitAdmin = new RabbitAdmin(cachingConnectionFactory());
//
//        rabbitAdmin.declareQueue(new Queue("my-queue", false, false, true));
//        rabbitAdmin.declareExchange(new DirectExchange("my-exchange", false, true));
//        rabbitAdmin.declareBinding(new Binding("my-queue", Binding.DestinationType.QUEUE, "my-exchange", "my-route", null));
//        return rabbitAdmin;
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        return new RabbitTemplate(cachingConnectionFactory());
//    }
////
////    @Bean
////    public SimpleMessageListenerContainer container(CachingConnectionFactory connectionFactory){
////        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
////        container.setQueueNames("myqueque");
////        container.setMessageListener(new My);
////    }
//
//}
