package com.bob.tesquare.rabbitmq;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "itcast2")
public class Customer2 {

    @RabbitHandler
    public void getMsg(String msg){
        System.out.println("2号消费者:"+ msg);
    }
}
