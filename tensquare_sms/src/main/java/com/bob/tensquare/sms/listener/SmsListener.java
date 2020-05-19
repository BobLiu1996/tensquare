package com.bob.tensquare.sms.listener;


import com.bob.tensquare.sms.utils.SmsUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtils smsUtils;


    @Value("${aliyun.sms.template_code}")
    private String template_code;//模板编号

    @Value("${aliyun.sms.sign_name}")
    private String sign_name;//签名

    /**
     * 消息队列中有消息时，则立即执行
     * @param map
     */
    @RabbitHandler
    public void executeSms(Map<String,String> map){
        String mobile=map.get("mobile");
        String checkCode=map.get("checkcode");
        System.out.println("手机号："+mobile);
        System.out.println("验证码："+checkCode);
        smsUtils.sendSms(mobile,template_code,sign_name, "{\"checkcode\":\""+checkCode+"\"}");
    }
}
