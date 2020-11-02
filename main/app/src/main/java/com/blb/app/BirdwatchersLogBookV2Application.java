package com.blb.app;

import com.blb.controller.BasicAuthController;
import com.blb.controller.ObservationController;
import com.blb.controller.UserController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = {"com.blb.controller","com.blb.service"},
        basePackageClasses = { BasicAuthController.class,
        ObservationController.class, UserController.class })
@EnableJpaRepositories(basePackages = {"com.blb.repository"})
@EntityScan(basePackages = {"com.blb.entity"})
public class BirdwatchersLogBookV2Application {

    public static void main(String[] args) {
        SpringApplication.run(BirdwatchersLogBookV2Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }
}
