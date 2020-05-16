package com.bob.tensquare.spit;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import util.IdWorker;

@SpringBootApplication
@CrossOrigin
public class SpitApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpitApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
