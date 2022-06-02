package com.github.moonfruit.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMain {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(SpringMain.class)));
    }
}
