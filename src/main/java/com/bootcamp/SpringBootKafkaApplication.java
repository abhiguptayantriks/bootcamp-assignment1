package com.bootcamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootKafkaApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(SpringBootKafkaApplication.class, args);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
