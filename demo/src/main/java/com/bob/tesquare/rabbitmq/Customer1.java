package com.bob.tesquare.rabbitmq;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "itcast1")
public class Customer1 {

    @RabbitHandler
    public void getMsg(String msg){
        System.out.println("1号消费者:"+ msg);
    }
}
