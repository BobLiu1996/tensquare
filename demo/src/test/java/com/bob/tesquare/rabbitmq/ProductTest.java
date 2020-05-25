package com.bob.tesquare.rabbitmq;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitmqApplication.class)
public class ProductTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMsg(){
        rabbitTemplate.convertAndSend("itcast","直接测试");
    }

    /**
     * 分列模式生产者
     */
    @Test
    public void sendMsg1(){
        rabbitTemplate.convertAndSend("exchangedemo","","分列模式1");
    }
    @Test
    public void sendMsg2(){
        rabbitTemplate.convertAndSend("exchangedemo","","分列模式2");
    }
    @Test
    public void sendMsg3(){
        rabbitTemplate.convertAndSend("exchangedemo","","分列模式3");
    }

    /**
     * 主题模式
     */

    @Test
    public void sendMsg4(){
        rabbitTemplate.convertAndSend("exchangeTopic","good.1","分列模式:itcast1");
    }

    @Test
    public void sendMsg5(){
        rabbitTemplate.convertAndSend("exchangeTopic","good.rer","分列模式:itcast3");
    }

    @Test
    public void sendMsg6(){
        rabbitTemplate.convertAndSend("exchangeTopic","good.log","分列模式:itcast2");
    }

}

