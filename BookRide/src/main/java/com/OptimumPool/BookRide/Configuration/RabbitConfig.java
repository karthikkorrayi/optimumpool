package com.OptimumPool.BookRide.Configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // FIXED: same exchange name as OfferRide
    public static final String EXCHANGE_NAME = "carpool_exchange";
    public static final String BOOKING_QUEUE = "booking_queue";
    public static final String OFFER_QUEUE   = "offer_queue";
    public static final String BOOKING_KEY   = "booking_route";
    public static final String OFFER_KEY     = "offer_route";

    @Bean
    public DirectExchange carpoolExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue bookingQueue() {
        return new Queue(BOOKING_QUEUE);
    }

    @Bean
    public Queue offerQueue() {
        return new Queue(OFFER_QUEUE);
    }

    @Bean
    public Binding bookingBinding() {
        return BindingBuilder.bind(bookingQueue()).to(carpoolExchange()).with(BOOKING_KEY);
    }

    @Bean
    public Binding offerBinding() {
        return BindingBuilder.bind(offerQueue()).to(carpoolExchange()).with(OFFER_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf) {
        RabbitTemplate rt = new RabbitTemplate(cf);
        rt.setMessageConverter(messageConverter());
        return rt;
    }
}