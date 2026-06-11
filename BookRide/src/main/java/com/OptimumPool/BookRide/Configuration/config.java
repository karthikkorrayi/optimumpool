package com.OptimumPool.BookRide.Configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class config {
    private String exchange_name="booking_exchange";
    private String queue_name="booking_queue";

    @Bean
    public DirectExchange directExchange()
    {
        return new DirectExchange(exchange_name) ;
    }
    @Bean
    public Queue registerQueue()
    {
        return new Queue(queue_name);
    }
    @Bean
    public Binding bindExcWithQue()
    {
        return  BindingBuilder.bind(registerQueue()).to(directExchange()).with("route_key");
    }
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory)
    {
        RabbitTemplate rt=new RabbitTemplate((ConnectionFactory) connectionFactory);
        rt.setMessageConverter(producerJackson2MessageConrt());
        return rt;

    }
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConrt()
    {
        return new Jackson2JsonMessageConverter();
    }

}
