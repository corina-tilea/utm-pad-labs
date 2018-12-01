/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corina.project.rest.ws.spring.boot;

import com.corina.project.rest.ws.spring.boot.client.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author corina
 */
@SpringBootApplication
public class AppConsumer {

    public static void main(String[] args) {
        SpringApplication.run(AppConsumer.class, args);
    }

    @Bean
    public Client client() {
        return new Client();
    }
}
