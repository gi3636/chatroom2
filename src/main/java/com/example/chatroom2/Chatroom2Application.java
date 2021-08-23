package com.example.chatroom2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Chatroom2Application {

    public static void main(String[] args) {
        SpringApplication.run(Chatroom2Application.class, args);
    }

}
