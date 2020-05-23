package com.bob.tensquare.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import util.IdWorker;

@CrossOrigin
@SpringBootApplication
@EnableEurekaClient
public class BaseApplication {
    /**
     * 当前微服务入口函数
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class);
    }

    /**
     * 使用@Bean注解配置该方法产生的对象放置于Spring容器中
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
